package com.travel.service;

import com.travel.entity.Order;
import com.travel.entity.TravelRoute;
import com.travel.repository.OrderRepository;
import com.travel.repository.TravelRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TravelRouteRepository travelRouteRepository;

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Map<String, Object> createOrder(Order order) {
        Map<String, Object> result = new HashMap<>();

        Optional<TravelRoute> routeOpt = travelRouteRepository.findById(order.getRouteId());
        if (!routeOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "旅游线路不存在");
            return result;
        }

        TravelRoute route = routeOpt.get();
        if (route.getStock() < order.getQuantity()) {
            result.put("success", false);
            result.put("message", "库存不足");
            return result;
        }

        route.setStock(route.getStock() - order.getQuantity());
        travelRouteRepository.save(route);

        order.setOrderNo(generateOrderNo());
        order.setTotalPrice(route.getPrice().multiply(new BigDecimal(order.getQuantity())));
        order.setStatus("待支付");
        order.setTravelDate(LocalDateTime.now().plusDays(7));

        Order savedOrder = orderRepository.save(order);

        result.put("success", true);
        result.put("message", "订单创建成功");
        result.put("order", savedOrder);
        return result;
    }

    @Transactional
    public Map<String, Object> updateOrderStatus(Long orderId, String status) {
        Map<String, Object> result = new HashMap<>();

        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "订单不存在");
            return result;
        }

        Order order = orderOpt.get();

        if ("已取消".equals(status) && !"已取消".equals(order.getStatus())) {
            Optional<TravelRoute> routeOpt = travelRouteRepository.findById(order.getRouteId());
            if (routeOpt.isPresent()) {
                TravelRoute route = routeOpt.get();
                route.setStock(route.getStock() + order.getQuantity());
                travelRouteRepository.save(route);
            }
        }

        order.setStatus(status);
        orderRepository.save(order);

        result.put("success", true);
        result.put("message", "状态更新成功");
        return result;
    }

    public Map<String, Object> payOrder(Long orderId) {
        return updateOrderStatus(orderId, "已支付");
    }

    public Map<String, Object> cancelOrder(Long orderId) {
        return updateOrderStatus(orderId, "已取消");
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private String generateOrderNo() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return "ORD" + date + uuid;
    }
}
