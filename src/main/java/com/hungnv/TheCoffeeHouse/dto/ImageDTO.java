package com.hungnv.TheCoffeeHouse.dto;
import lombok.*;


public class ImageDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse {
        private String secureUrl;
        private String publicId;

    }

}
