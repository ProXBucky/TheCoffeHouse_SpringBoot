package com.hungnv.TheCoffeeHouse.dto;
import com.hungnv.TheCoffeeHouse.model.Allcodes;
import com.hungnv.TheCoffeeHouse.model.Products;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

public class ProductDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponseDTO {
        private List<Products> data;
        private int totalPages;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private String category;
        private String image;
        private Integer originalPrice;
        private Integer quantitySold;
        private String cloudId;
        private Allcodes categoryData;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDescription {
        private Long id;
        private String name;
        private String image;
        private Integer originalPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCreateRequest {
        private String name;
        private String description;
        private String categoryId;
        private String image;
        private Integer originalPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductEditRequest {
        private Long id;
        private String name;
        private String description;
        private String categoryId;
        private String image;
        private Integer originalPrice;
    }

}
