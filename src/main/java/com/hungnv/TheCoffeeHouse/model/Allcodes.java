package com.hungnv.TheCoffeeHouse.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Allcodes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "keyMap")
})
@JsonIgnoreProperties({"products", "stores", "orders", "users"})
public class Allcodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "keyMap", nullable = false, length = 100, unique = true)
    private String keyMap;

    @Column(name = "valueEn", length = 255)
    private String valueEn;

    @Column(name = "valueVn", length = 255)
    private String valueVn;

    // Relationships
    @OneToMany(mappedBy = "categoryData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Products> products;

    @OneToMany(mappedBy = "cityData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Stores> stores;

    @OneToMany(mappedBy = "statusData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Orders> orders;

    @OneToMany(mappedBy = "roleData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Users> users;
}
