package com.hungnv.TheCoffeeHouse.controller;
import com.hungnv.TheCoffeeHouse.dto.CommonDTO;
import com.hungnv.TheCoffeeHouse.dto.StoreDTO;
import com.hungnv.TheCoffeeHouse.model.Allcodes;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @GetMapping("/type/{typeParam}")
    public ResponseEntity<Object> getByType(@PathVariable String typeParam) {
        List<Allcodes> allcode = appService.getAllByType(typeParam);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", allcode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<Object> getBestSeller(@RequestParam(required = false, defaultValue = "10") Long limit) {
        List<Products> products = appService.getBestSeller(limit);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistic")
    public ResponseEntity<Object> getStatistic(){
        CommonDTO.StatisticDTO res = appService.getStatistic();
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("data", res);
        return ResponseEntity.ok(response);
    }


}
