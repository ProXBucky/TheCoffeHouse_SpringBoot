package com.hungnv.TheCoffeeHouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterResponse {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
        private String roleId;
        private int isApproved;

        public RegisterResponse(RegisterResponse add) {
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String email;
        private String token;
    }

}


