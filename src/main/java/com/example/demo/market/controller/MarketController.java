package com.example.demo.market.controller;

import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.service.MarketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/markets")
public class MarketController {

    @Autowired
    private final MarketService marketService;

    @GetMapping("/syncMarket")
    public ResponseEntity<List<MarketEntity>> syncMarket(){
        return  marketService.syncMarket();
    }
    @GetMapping()
    public ResponseEntity<List<MarketEntity>> getAllMarkets(){
        return  marketService.getAllMarkets();
    }

}
