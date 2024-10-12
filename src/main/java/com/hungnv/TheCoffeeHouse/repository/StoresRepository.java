package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.model.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoresRepository extends JpaRepository<Stores, Long> {
    Page<Stores> findByCityId(String cityId, Pageable pageable);

    @Query(value = "SELECT p.id, p.name_store, p.address, p.description, p.city_id, " +
            "p.short_description, p.map_link, p.map_HTML FROM Stores p WHERE p.city_id = :cityId LIMIT :limit", nativeQuery = true)
    List<Stores> findTopNByCityId(@Param("cityId") String cityId, @Param("limit") int limit);

    @Query(value = "SELECT p.id, p.name_store, p.address, p.description, p.city_id, " +
            "p.short_description, p.map_link, p.map_HTML FROM Stores p ORDER BY p.id DESC LIMIT :limit", nativeQuery = true)
    List<Stores> findTopN(@Param("limit") int limit);
}
