package com.travel.controller;

import com.travel.entity.User;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        return userService.login(username, password);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id).orElse(null);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username).orElse(null);
    }
}
