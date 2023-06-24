package com.example.demo.instrument.controller;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insturments")
public class InstrumentController {

    @Autowired
    InstrumentService instrumentService;


    @GetMapping("/{symbol}")
    public InstrumentEntity getBySymbol(@PathVariable String symbol){
        return instrumentService.findBySymbol(symbol);
    }

    @GetMapping("/getAllInstruments")
    public List<InstrumentEntity> getAll(){
        return instrumentService.getAllInstruments();
    }

    @GetMapping("/syncInstruments")
    public ResponseEntity<List<InstrumentEntity>> syncInstruments(){
        return instrumentService.syncInstruments();
    }
}
