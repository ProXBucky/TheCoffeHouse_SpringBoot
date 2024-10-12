package com.hungnv.TheCoffeeHouse.controller;

import com.hungnv.TheCoffeeHouse.dto.ProductDTO;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.service.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductDTO.ProductCreateRequest data) throws IOException {
        Products products = productService.create(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> delete(@PathVariable Long productId) throws IOException {
        try {
            productService.delete(productId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Delete success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete store: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ProductDTO.ProductEditRequest data) throws IOException {
        Products products = productService.update(data);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllByCategory(
            @RequestParam(required = false, defaultValue = "ALL") String category,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestParam(required = false, defaultValue = "0") Integer limit
    ) {
        Optional<ProductDTO.ProductResponseDTO> response = productService.getAllByCategory(category, page, itemsPerPage, limit);

        // Tạo Map chứa các trường status, message, và data
        Map<String, Object> responseBody = new HashMap<>();

        if (response.isPresent()) {
            responseBody.put("status", HttpStatus.OK.value());
            responseBody.put("message", "Success");
            responseBody.put("data", response.get());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            responseBody.put("status", HttpStatus.NOT_FOUND.value());
            responseBody.put("message", "No products found for the specified category.");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> get(@PathVariable Long productId){
        ProductDTO.ProductResponse productResponse = productService.get(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", productResponse);
        return ResponseEntity.ok(response);
    }
}

