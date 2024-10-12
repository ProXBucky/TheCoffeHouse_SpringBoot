package com.hungnv.TheCoffeeHouse.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommonDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticDTO {
        private Long totalAdmins;
        private Long totalProducts;
        private Long totalStores;
        private Long totalOrders;
        private Long totalIncomes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllcodesDTO {
        private Long id;
        private String type;
        private String keyMap;
        private String valueEn;
        private String valueVn;
    }




}
