package com.hungnv.TheCoffeeHouse.dto;

import com.hungnv.TheCoffeeHouse.model.OrderDetail;
import com.hungnv.TheCoffeeHouse.model.Orders;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusOrder {
        private String statusVN;
        private String statusEN;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponse {
        private Orders order;
        private UserDTO.UserResponse user;
        private StatusOrder statusOrder;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailOrderResponse {
        private OrderDetail orderDetail;
        private ProductDTO.ProductDescription product;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponseFull {
        private Orders order;
        private UserDTO.UserResponse user;
        private StatusOrder statusOrder;
        private List<DetailOrderResponse> detailOrderResponses;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderRequest {
        private Long orderId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        private Long productId;
        private Long quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequest {
        private String email;
        private String firstName;
        private String lastName;
        private String phone;
        private String address;
        private String timeOrder;
        private Long cartTotalAmount;
        private List<CartItem> cartItems;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewOrderResponse {
        private String email;
        private String firstName;
        private String lastName;
        private String phone;
        private String address;
        private Orders orders;
        private List<OrderDetail> cartItems;
    }
}
