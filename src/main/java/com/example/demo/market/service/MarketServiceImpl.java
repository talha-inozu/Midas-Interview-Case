package com.example.demo.market.service;


import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.repository.MarketRepository;
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


@AllArgsConstructor
@Service
public class MarketServiceImpl implements MarketService{
    private static final Logger log = LoggerFactory.getLogger(MarketServiceImpl.class);
    @Autowired
    private final MarketRepository marketRepository;
    @Autowired
    private final RestTemplate restTemplate;

    //update all markets by using robinhood api
    @Override
    public ResponseEntity<List<MarketEntity>> syncMarket() {
        List<MarketEntity> response = new ArrayList<>();
        log.info("Sync Markets started");
        try{

            ResponseEntity<String> result = restTemplate.exchange("https://api.robinhood.com/markets", HttpMethod.GET,null,String.class);

            JSONObject resultJsonObject = new JSONObject(result.getBody());

            JSONArray marketArray = resultJsonObject.getJSONArray("results");


            for (int i = 0; i < marketArray.length(); i++) {

                try{
                    JSONObject marketJsonObject = marketArray.getJSONObject(i);
                    MarketEntity marketEntity = marketRepository.findMarketByCode(marketJsonObject.getString("mic"));
                    //if market exists update else save to database
                    if (marketEntity != null) {
                        marketEntity.setSymbol(marketJsonObject.getString("acronym"));
                        marketEntity.setName(marketJsonObject.getString("name"));
                        marketEntity.setCountry(marketJsonObject.getString("country"));
                        marketEntity.setWebsite(marketJsonObject.getString("website"));
                        response.add(marketRepository.save(marketEntity));

                    } else {
                        response.add(marketRepository.save(
                                MarketEntity.builder().
                                        code(marketJsonObject.getString("mic")).
                                        country(marketJsonObject.getString("country")).
                                        symbol(marketJsonObject.getString("acronym")).
                                        name(marketJsonObject.getString("name")).
                                        website(marketJsonObject.getString("website"))
                                        .build()));
                    }
                }catch (Exception e){
                    log.error("ERROR at MarketService :" + e.getMessage());
                }

            }


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        log.info("Sync Markets completed");
        return ResponseEntity.ok(response);
    }

    @Override
    public MarketEntity findMarketByCode(String code) {
        try {
            return marketRepository.findMarketByCode(code);
        }catch (Exception e){
            log.error("ERROR at MarketService :" + e.getMessage());
            return null;
        }

    }
}
