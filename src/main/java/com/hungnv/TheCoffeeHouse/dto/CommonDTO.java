package com.hungnv.TheCoffeeHouse.dto;
import lombok.*;

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
