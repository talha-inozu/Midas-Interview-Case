package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;

import java.util.List;

public interface InstrumentService {

    public InstrumentEntity findBySymbol(String symbol);

    public List<InstrumentEntity> getAllInstruments();
}
