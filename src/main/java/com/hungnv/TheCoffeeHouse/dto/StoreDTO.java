package com.hungnv.TheCoffeeHouse.dto;

import com.hungnv.TheCoffeeHouse.model.ImageStore;
import lombok.*;

import java.util.List;

public class StoreDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreRequest {
        private String nameStore;
        private String address;
        private String description;
        private String cityId;
        private String shortDescription;
        private String mapLink;
        private String mapHTML;
        private List<String> image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageData {
        private String base64Image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreRequestWithId {
        private Long id;
        private String nameStore;
        private String address;
        private String description;
        private String cityId;
        private String shortDescription;
        private String mapLink;
        private String mapHTML;
        private List<String> image;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseStoreResponse {
        private Long id;
        private String nameStore;
        private String address;
        private String description;
        private String cityId;
        private String shortDescription;
        private String mapLink;
        private String mapHTML;
        private List<ImageStore> image;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreResponse {
        private Long id;
        private String nameStore;
        private String address;
        private String description;
        private String cityId;
        private String shortDescription;
        private String mapLink;
        private String mapHTML;
        private String cityName;
        private List<ImageStore> image;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreResponseDTO {
        private List<StoreResponse> data;
        private int totalPages;
    }
}
