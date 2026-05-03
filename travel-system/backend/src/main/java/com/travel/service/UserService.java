package com.travel.service;

import com.travel.entity.User;
import com.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();

        if (userRepository.existsByUsername(user.getUsername())) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        userRepository.save(user);
        result.put("success", true);
        result.put("message", "注册成功");
        result.put("user", user);
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            result.put("success", false);
            result.put("message", "用户不存在");
            return result;
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            result.put("success", false);
            result.put("message", "密码错误");
            return result;
        }

        result.put("success", true);
        result.put("message", "登录成功");
        result.put("user", user);
        return result;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
