package com.hungnv.TheCoffeeHouse.controller;

import com.hungnv.TheCoffeeHouse.dto.AuthDTO;
import com.hungnv.TheCoffeeHouse.dto.CommonDTO;
import com.hungnv.TheCoffeeHouse.dto.UserDTO;
import com.hungnv.TheCoffeeHouse.model.Users;
import com.hungnv.TheCoffeeHouse.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AuthDTO.RegisterRequest data){
        AuthDTO.RegisterResponse admin = adminService.add(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", admin);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/approved/{adminId}")
    public ResponseEntity<Object> approve(@PathVariable Long adminId){
        UserDTO.UserResponse admin =  adminService.approve(adminId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", admin);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Object> delete(@PathVariable Long adminId){
        adminService.delete(adminId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Delete success");
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody UserDTO.UserUpdate data) {
        UserDTO.UserResponse admin = adminService.update(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", admin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Object> getById(@PathVariable int adminId){
        UserDTO.UserResponse admin = adminService.getById(adminId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", admin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allNotApproved")
    public ResponseEntity<Object> getAllNotApproved(){
        List<UserDTO.UserResponseWithRole> adminList = adminService.getAllNotApproved();
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", adminList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(){
        List<UserDTO.UserResponseWithRole> adminList = adminService.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", adminList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/author")
    public ResponseEntity<Object> author(HttpServletRequest request) {
        String res = adminService.author(request); // Truyền request vào phương thức author
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

}
