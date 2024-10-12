package com.hungnv.TheCoffeeHouse.service;
import com.hungnv.TheCoffeeHouse.dto.CommonDTO;
import com.hungnv.TheCoffeeHouse.model.Allcodes;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AllcodesRepository allcodesRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final StoresRepository storesRepository;
    private final OrderRepository orderRepository;


    public List<Allcodes> getAllByType(String typeParam) {
        return allcodesRepository.findAllByType(typeParam);
    }

    public List<Products> getBestSeller(Long limit){
        return productRepository.findTopByOrderByQuantitySold(Math.toIntExact(limit));
    }

    @Transactional(readOnly = true)
    public CommonDTO.StatisticDTO getStatistic(){
        Long totalAdmins = usersRepository.countByRoleIdInAndIsApproved(Arrays.asList("R1", "R2"), 1);
        Long totalProducts = productRepository.count();
        Long totalStores = storesRepository.count();
        Long totalOrders = orderRepository.count();
        Long totalIncome = orderRepository.sumTotalPriceByStatusId(Arrays.asList("SP2", "SP3"));
        return new CommonDTO.StatisticDTO(
                totalAdmins, totalProducts, totalStores, totalOrders, totalIncome
        );
    }
}
