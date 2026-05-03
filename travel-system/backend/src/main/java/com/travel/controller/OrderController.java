package com.travel.controller;

import com.travel.entity.Order;
import com.travel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id).orElse(null);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.findByUserId(userId);
    }

    @PostMapping
    public Map<String, Object> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}/status")
    public Map<String, Object> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> data) {
        String status = data.get("status");
        return orderService.updateOrderStatus(id, status);
    }

    @PutMapping("/{id}/pay")
    public Map<String, Object> payOrder(@PathVariable Long id) {
        return orderService.payOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public Map<String, Object> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
