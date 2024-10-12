package com.hungnv.TheCoffeeHouse.controller;
import com.hungnv.TheCoffeeHouse.dto.StoreDTO;
import com.hungnv.TheCoffeeHouse.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody StoreDTO.StoreRequest data) throws IOException {
        StoreDTO.BaseStoreResponse storeResponse = storeService.create(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", storeResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Object> delete(@PathVariable Long storeId) throws IOException {
        try {
            storeService.delete(storeId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Delete success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete store: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody StoreDTO.StoreRequestWithId data) throws IOException {
        StoreDTO.BaseStoreResponse storeResponse = storeService.update(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", storeResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStoreByCity(
            @RequestParam(required = false, defaultValue = "ALL") String city,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestParam(required = false, defaultValue = "0") Integer limit
    ) {
        // Gọi Service để lấy dữ liệu
        Optional<StoreDTO.StoreResponseDTO> response = storeService.getAllStoreByCity(city, page, itemsPerPage, limit);

        // Tạo Map chứa các trường status, message và data
        Map<String, Object> responseBody = new HashMap<>();

        if (response.isPresent()) {
            responseBody.put("status", HttpStatus.OK.value());
            responseBody.put("message", "Success");
            responseBody.put("data", response.get());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            responseBody.put("status", HttpStatus.NOT_FOUND.value());
            responseBody.put("message", "No stores found for the specified city.");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Object> get(@PathVariable Long storeId) throws IOException {
        StoreDTO.StoreResponse storeResponse = storeService.get(storeId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", storeResponse);
        return ResponseEntity.ok(response);
    }
}

