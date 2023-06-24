package com.example.demo.market.service;

import com.example.demo.market.entity.MarketEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarketService {


    public ResponseEntity<List<MarketEntity>> syncMarket();
    public ResponseEntity<List<MarketEntity>> getAllMarkets();
    public MarketEntity findByCode(String ccode);


}
