package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageStoreRepository extends JpaRepository<ImageStore, Long> {
    List<ImageStore> findAllByStoreId(Long storeId);
}
