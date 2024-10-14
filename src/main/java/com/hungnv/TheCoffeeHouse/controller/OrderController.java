package com.hungnv.TheCoffeeHouse.controller;
import com.hungnv.TheCoffeeHouse.dto.OrderDTO;
import com.hungnv.TheCoffeeHouse.model.Orders;
import com.hungnv.TheCoffeeHouse.service.OrderService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/lastest")
    public ResponseEntity<Object> getLastest(@RequestParam("limit") int limit) {
        List<OrderDTO.OrderResponse> res = orderService.getLastest(limit);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-delivered")
    public ResponseEntity<Object> getAllDelivered() {
        List<OrderDTO.OrderResponseFull> res = orderService.getAllDelivered();
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        List<OrderDTO.OrderResponseFull> res = orderService.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Object> delete(@PathVariable Long orderId) {
        try {
            orderService.delete(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Delete success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete: " + e.getMessage());
        }
    }

    @PutMapping("/pay")
    public ResponseEntity<Object> updatePay(@RequestBody OrderDTO.UpdateOrderRequest body){
        Orders res = orderService.updatePay(body);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delivery")
    public ResponseEntity<Object> updateDelivery(@RequestBody OrderDTO.UpdateOrderRequest body){
        Orders res = orderService.updateDelivery(body);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Object> add(@RequestBody OrderDTO.OrderRequest body){
        OrderDTO.NewOrderResponse res = orderService.add(body);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }

}
