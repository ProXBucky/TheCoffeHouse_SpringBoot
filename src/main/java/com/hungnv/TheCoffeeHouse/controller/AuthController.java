package com.hungnv.TheCoffeeHouse.controller;

import com.hungnv.TheCoffeeHouse.dto.AuthDTO;
import com.hungnv.TheCoffeeHouse.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthDTO.RegisterRequest registerRequest) {
        AuthDTO.RegisterResponse res = authService.register(registerRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthDTO.AuthRequest authRequest) {
        AuthDTO.AuthResponse res = authService.login(authRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }
}
