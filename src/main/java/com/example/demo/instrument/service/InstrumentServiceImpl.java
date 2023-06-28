package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.repository.InstrumentRepository;
import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.service.MarketService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {
    private static final Logger log = LoggerFactory.getLogger(InstrumentServiceImpl.class);
    @Autowired
    private final InstrumentRepository instrumentRepository;

    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final MarketService marketService;

    @Override
    public ResponseEntity<InstrumentEntity> findInstrumentBySymbol(String symbol) {
        try {
            return ResponseEntity.ok(instrumentRepository.findInstrumentBySymbol(symbol));
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

    //update all instruments by executing request for each instrument
    @Override
    public ResponseEntity<List<InstrumentEntity>> syncInstruments() {
        List<InstrumentEntity> response = new ArrayList<>();
        List<InstrumentEntity> instrumentEntityList = instrumentRepository.findAll();

        log.info("Sync Instruments started");

        for (InstrumentEntity instrumentEntity : instrumentEntityList) {

            try {
                ResponseEntity<String> result = restTemplate.exchange("https://api.robinhood.com/instruments/?symbol=" + instrumentEntity.getSymbol(), HttpMethod.GET, null, String.class);

                JSONObject instrument = new JSONObject(result.getBody());

                JSONArray results = instrument.getJSONArray("results");

                if (results.length() > 0) {
                    instrument = results.getJSONObject(0);

                    //split market url for define  market code
                    String marketString[] = instrument.getString("market").split("/");
                    String marketCode = marketString[marketString.length - 1];

                    MarketEntity marketEntity = marketService.findMarketByCode(marketCode);

                    // -1 means market doesn't exist or not updated yet
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
