package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Page<Products> findByCategoryId(String categoryId, Pageable pageable);

    @Query(value = "SELECT p.id, p.name, p.description, p.category_id, p.image, p.original_price, p.quantity_sold, p.cloud_id FROM Products p ORDER BY p.quantity_sold DESC LIMIT :limit", nativeQuery = true)
    List<Products> findTopByOrderByQuantitySold(@Param("limit") int limit);

    @Query(value = "SELECT p.id, p.name, p.description, p.category_id, p.image, p.original_price, p.quantity_sold, p.cloud_id FROM Products p WHERE p.category_id = :categoryId ORDER BY p.quantity_sold DESC LIMIT :limit", nativeQuery = true)
    List<Products> findTopNByCategoryId(@Param("categoryId") String categoryId, @Param("limit") int limit);

    @Query(value = "SELECT p.id, p.name, p.description, p.category_id, p.image, p.original_price, p.quantity_sold, p.cloud_id FROM Products p ORDER BY p.quantity_sold DESC LIMIT :limit", nativeQuery = true)
    List<Products> findTopN(@Param("limit") int limit);
}