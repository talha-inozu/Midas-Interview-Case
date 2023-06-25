package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InstrumentService {

    public ResponseEntity<InstrumentEntity> findInstrumentBySymbol(String symbol);

    public ResponseEntity<List<InstrumentEntity>> getAllInstruments();
    public ResponseEntity<List<InstrumentEntity>> syncInstruments();

}
