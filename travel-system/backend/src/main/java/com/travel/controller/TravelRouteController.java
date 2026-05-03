package com.travel.controller;

import com.travel.entity.TravelRoute;
import com.travel.service.TravelRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "http://localhost:3000")
public class TravelRouteController {

    @Autowired
    private TravelRouteService travelRouteService;

    @GetMapping
    public List<TravelRoute> getAllRoutes() {
        return travelRouteService.findAll();
    }

    @GetMapping("/{id}")
    public TravelRoute getRouteById(@PathVariable Long id) {
        return travelRouteService.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<TravelRoute> searchRoutes(@RequestParam(required = false) String keyword) {
        return travelRouteService.searchRoutes(keyword);
    }

    @GetMapping("/destination/{destination}")
    public List<TravelRoute> getRoutesByDestination(@PathVariable String destination) {
        return travelRouteService.findByDestination(destination);
    }

    @GetMapping("/available")
    public List<TravelRoute> getAvailableRoutes() {
        return travelRouteService.findAvailableRoutes();
    }

    @PostMapping
    public Map<String, Object> addRoute(@RequestBody TravelRoute route) {
        return travelRouteService.addRoute(route);
    }

    @PutMapping("/{id}/stock")
    public Map<String, Object> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> data) {
        Integer quantity = data.get("quantity");
        return travelRouteService.updateStock(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        travelRouteService.deleteById(id);
    }
}
