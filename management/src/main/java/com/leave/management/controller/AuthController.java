package com.leave.management.controller;

import com.leave.management.model.Employee;
import com.leave.management.repository.EmployeeRepository;
import com.leave.management.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeRepository repo;

    @PostMapping("/register")
    public Employee register(@RequestBody Employee emp) {
        return repo.save(emp);
    }

    @PostMapping("/login")
    public String login(@RequestBody Employee emp) {

        Employee user = repo.findByEmail(emp.getEmail())
                .orElseThrow();

        if (!user.getPassword().equals(emp.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(user.getEmail()); // 🔥 return token
    }
}