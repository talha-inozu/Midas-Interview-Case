package com.example.demo.instrument.controller;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.service.InstrumentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/instruments")
public class InstrumentController {

    @Autowired
    private final InstrumentService instrumentService;


    @GetMapping("/{symbol}")
    public ResponseEntity<InstrumentEntity> getInstrumentBySymbol(@PathVariable String symbol){
        return instrumentService.findInstrumentBySymbol(symbol);
    }

    @GetMapping
    public ResponseEntity<List<InstrumentEntity>> getAllInstruments(){
        return instrumentService.getAllInstruments();
    }

    @GetMapping("/syncInstruments")
    public ResponseEntity<List<InstrumentEntity>> syncInstruments(){
        return instrumentService.syncInstruments();
    }
}
