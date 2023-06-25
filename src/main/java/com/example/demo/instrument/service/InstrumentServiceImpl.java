package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.repository.InstrumentRepository;
import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class InstrumentServiceImpl implements InstrumentService {
    private static final Logger log = LoggerFactory.getLogger(InstrumentServiceImpl.class);
    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    MarketService marketService;

    @Override
    public ResponseEntity<InstrumentEntity> findInstrumentBySymbol(String symbol) {
        try {
            return ResponseEntity.ok(instrumentRepository.findBySymbol(symbol));
        }catch (Exception e){
            log.error("ERROR at InstrumentService :" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity<List<InstrumentEntity>> getAllInstruments() {
        try{
            return ResponseEntity.ok(instrumentRepository.findAll());
        }catch (Exception e){
            log.error("ERROR at InstrumentService :" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity<List<InstrumentEntity>> syncInstruments() {
        List<InstrumentEntity> response = new ArrayList<>();
        List<InstrumentEntity> instrumentEntityList = instrumentRepository.findAll();
        RestTemplate restTemplate = new RestTemplate();

        log.info("Sync Instruments started");

        for (InstrumentEntity instrumentEntity : instrumentEntityList) {

            try {
                ResponseEntity<String> result = restTemplate.exchange("https://api.robinhood.com/instruments/?symbol=" + instrumentEntity.getSymbol(), HttpMethod.GET, null, String.class);

                JSONObject instrument = new JSONObject(result.getBody());

                JSONArray results = instrument.getJSONArray("results");

                if (results.length() > 0) {
                    instrument = results.getJSONObject(0);

                    String marketString[] = instrument.getString("market").split("/");
                    String marketCode = marketString[marketString.length - 1];

                    MarketEntity marketEntity = marketService.findMarketByCode(marketCode);

                    Long marketId = marketEntity != null ? marketEntity.getId() : -1;

                    instrumentEntity.setCustom_name(instrument.getString("simple_name"));
                    instrumentEntity.setName(instrument.getString("name"));
                    instrumentEntity.setMarket_id(marketId);
                    response.add(instrumentRepository.save(instrumentEntity));
                }

            } catch (Exception e) {
                log.error("ERROR at InstrumentService :" + e.getMessage());
            }

        }

        log.info("Sync Instruments completed");
        return ResponseEntity.ok(response);
    }


}
