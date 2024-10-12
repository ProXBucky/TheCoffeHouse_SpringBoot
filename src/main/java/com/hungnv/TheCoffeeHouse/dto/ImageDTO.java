package com.hungnv.TheCoffeeHouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ImageDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse {
        private String secureUrl;
        private String publicId;

    }

}
