package com.hungnv.TheCoffeeHouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderId", nullable = false)
    private Long orderId; // Đổi thành Long để phù hợp với kiểu dữ liệu id

    @Column(name = "productId", nullable = false)
    private Long productId; // Đổi thành Long để phù hợp với kiểu dữ liệu id

    @Column(name = "quantity")
    private Integer quantity;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    @JsonIgnore
    private Orders orderData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    @JsonIgnore
    private Products productData;
}