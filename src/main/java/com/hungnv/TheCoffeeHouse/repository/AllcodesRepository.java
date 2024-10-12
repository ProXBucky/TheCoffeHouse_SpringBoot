package com.hungnv.TheCoffeeHouse.repository;

import com.hungnv.TheCoffeeHouse.model.Allcodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllcodesRepository extends JpaRepository<Allcodes, Long> {
    List<Allcodes> findAllByType(String type);
}
