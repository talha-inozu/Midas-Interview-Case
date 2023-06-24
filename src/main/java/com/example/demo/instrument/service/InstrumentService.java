package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.market.entity.MarketEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InstrumentService {

    public InstrumentEntity findBySymbol(String symbol);

    public List<InstrumentEntity> getAllInstruments();
    public ResponseEntity<List<InstrumentEntity>> syncInstruments();

}
