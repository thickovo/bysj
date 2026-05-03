package com.travel.service;

import com.travel.entity.TravelRoute;
import com.travel.repository.TravelRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TravelRouteService {

    @Autowired
    private TravelRouteRepository travelRouteRepository;

    public List<TravelRoute> findAll() {
        return travelRouteRepository.findAll();
    }

    public Optional<TravelRoute> findById(Long id) {
        return travelRouteRepository.findById(id);
    }

    public List<TravelRoute> searchRoutes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return travelRouteRepository.findAll();
        }
        return travelRouteRepository.searchByKeyword(keyword);
    }

    public List<TravelRoute> findByDestination(String destination) {
        return travelRouteRepository.findByDestinationContaining(destination);
    }

    public List<TravelRoute> findAvailableRoutes() {
        return travelRouteRepository.findByStockGreaterThan(0);
    }

    public Map<String, Object> addRoute(TravelRoute route) {
        Map<String, Object> result = new HashMap<>();
        TravelRoute saved = travelRouteRepository.save(route);
        result.put("success", true);
        result.put("message", "添加成功");
        result.put("route", saved);
        return result;
    }

    @Transactional
    public Map<String, Object> updateStock(Long routeId, Integer quantity) {
        Map<String, Object> result = new HashMap<>();

        Optional<TravelRoute> routeOpt = travelRouteRepository.findById(routeId);
        if (!routeOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "线路不存在");
            return result;
        }

        TravelRoute route = routeOpt.get();
        if (route.getStock() < quantity) {
            result.put("success", false);
            result.put("message", "库存不足");
            return result;
        }

        route.setStock(route.getStock() - quantity);
        travelRouteRepository.save(route);

        result.put("success", true);
        result.put("message", "库存更新成功");
        return result;
    }

    public TravelRoute save(TravelRoute route) {
        return travelRouteRepository.save(route);
    }

    public void deleteById(Long id) {
        travelRouteRepository.deleteById(id);
    }
}
