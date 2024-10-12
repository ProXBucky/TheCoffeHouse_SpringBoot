package com.hungnv.TheCoffeeHouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId; // Đổi thành Long để phù hợp với Users

    @Column(name = "totalPrice")
    private Long totalPrice;

    @Column(name = "statusId")
    private String statusId;

    @Column(name = "timeOrder")
    private String timeOrder;

    // Relationship to Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Users user;

    // Relationship to Allcodes for status
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId", referencedColumnName = "keyMap", insertable = false, updatable = false)
    @JsonIgnore
    private Allcodes statusData;
}
