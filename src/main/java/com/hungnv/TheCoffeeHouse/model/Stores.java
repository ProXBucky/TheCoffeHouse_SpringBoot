package com.hungnv.TheCoffeeHouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stores")
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameStore", nullable = false, length = 255)
    private String nameStore;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cityId", nullable = false, length = 50)
    private String cityId;

    @Column(name = "shortDescription", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "mapLink", columnDefinition = "TEXT")
    private String mapLink;

    @Column(name = "mapHtml", columnDefinition = "TEXT")
    private String mapHTML;

    // Relationship to Allcodes
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", referencedColumnName = "keyMap", insertable = false, updatable = false)
    @JsonIgnore
    private Allcodes cityData;

}
