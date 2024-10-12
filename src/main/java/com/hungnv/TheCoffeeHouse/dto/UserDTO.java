package com.hungnv.TheCoffeeHouse.dto;

import lombok.*;

public class UserDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
        private String roleId;
        private int isApproved;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseWithRole {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
        private int isApproved;
        private String roleValueEn;
        private String roleValueVn;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUpdate{
        private Long id;
        private String password;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }

}
