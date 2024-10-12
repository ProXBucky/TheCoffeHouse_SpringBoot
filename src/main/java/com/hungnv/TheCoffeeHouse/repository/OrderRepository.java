package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.Orders;
import com.hungnv.TheCoffeeHouse.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.statusId IN (:statusId)")
    Long sumTotalPriceByStatusId(List<String> statusId);

    @Query(value = "SELECT p.id, p.user_id, p.total_price, p.status_id, p.time_order " +
            "FROM Orders p " +
            "ORDER BY TO_TIMESTAMP(p.time_order, 'DD/MM/YYYY, HH12:MI AM') DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Orders> findTopN(@Param("limit") int limit);



    @Query("SELECT o FROM Orders o WHERE o.statusId IN (:statusId)")
    List<Orders> findAllOrdersByStatusId(List<String> statusId);
}
