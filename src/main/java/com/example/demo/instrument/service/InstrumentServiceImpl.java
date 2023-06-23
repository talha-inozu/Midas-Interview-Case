package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InstrumentServiceImpl implements InstrumentService {


    @Autowired
    InstrumentRepository instrumentRepository;

    @Override
    public InstrumentEntity findBySymbol(String symbol) {
        return instrumentRepository.findBySymbol(symbol);
    }

    @Override
    public List<InstrumentEntity> getAllInstruments() {
        return  instrumentRepository.findAll();
    }


}
