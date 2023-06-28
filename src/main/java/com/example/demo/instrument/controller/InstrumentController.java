package com.example.demo.instrument.controller;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.service.InstrumentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/instruments")
public class InstrumentController {

    @Autowired
    private final InstrumentService instrumentService;


    @GetMapping("/")
    public ResponseEntity<InstrumentEntity> getInstrumentBySymbol(@RequestParam String symbol){
        return instrumentService.findInstrumentBySymbol(symbol);
    }

    @GetMapping
    public ResponseEntity<List<InstrumentEntity>> getAllInstruments(){
        return instrumentService.getAllInstruments();
    }

    @GetMapping("/sync")
    public ResponseEntity<List<InstrumentEntity>> syncInstruments(){
        return instrumentService.syncInstruments();
    }
}
