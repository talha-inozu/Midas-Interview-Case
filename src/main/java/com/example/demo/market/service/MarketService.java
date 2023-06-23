package com.example.demo.market.service;

import com.example.demo.market.entity.MarketEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarketService {

    public MarketEntity getById(Long id);
    public MarketEntity getBySymbol(String symbol);
    public ResponseEntity<List<MarketEntity>> syncMarket();

    public ResponseEntity<List<MarketEntity>> getAllMarkets();
}
