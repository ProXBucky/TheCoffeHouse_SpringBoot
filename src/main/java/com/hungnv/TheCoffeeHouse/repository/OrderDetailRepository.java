package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderId(Long orderId);
    void deleteAllByOrderId(Long orderId);
}
