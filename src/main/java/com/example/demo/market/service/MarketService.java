package com.example.demo.market.service;

import com.example.demo.market.entity.MarketEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarketService {

    public ResponseEntity<List<MarketEntity>> syncMarket();
    public MarketEntity findMarketByCode(String ccode);

}
