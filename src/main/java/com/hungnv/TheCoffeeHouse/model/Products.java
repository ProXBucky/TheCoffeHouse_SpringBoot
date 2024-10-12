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
@Table(name = "Products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "categoryId", nullable = false, length = 50)
    private String categoryId;

    private String image;

    @Column(name = "originalPrice")
    private Integer originalPrice;

    @Column(name = "quantitySold")
    private Integer quantitySold;

    private String cloudId;

    // Relationship to Allcodes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "keyMap", insertable = false, updatable = false)
    @JsonIgnore
    private Allcodes categoryData;
}
