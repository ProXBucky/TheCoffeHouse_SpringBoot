package com.hungnv.TheCoffeeHouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ImageStore")
public class ImageStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "storeId", nullable = false)
    private Long storeId; // Đổi thành Long để phù hợp với kiểu dữ liệu id

    @Column(name = "image")
    private String image;

    @Column(name = "cloudId")
    private String cloudId;

    // Relationship with Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", insertable = false, updatable = false)
    @JsonIgnore
    private Stores storeData;
}