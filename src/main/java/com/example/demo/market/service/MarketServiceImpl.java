package com.example.demo.market.service;

import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.repository.MarketRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService{

    @Autowired
    MarketRepository marketRepository;


    @Override
    public MarketEntity getById(Long id) {
        return marketRepository.getById(id);
    }

    @Override
    public MarketEntity getBySymbol(String symbol) {
        return  marketRepository.findBySymbol(symbol);
    }


    @Override
    public ResponseEntity<List<MarketEntity>> syncMarket() {
        List<MarketEntity> response = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        try{

            ResponseEntity<String> result = restTemplate.exchange("https://api.robinhood.com/markets", HttpMethod.GET,null,String.class);

            JSONObject jsonObject = new JSONObject(result.getBody());

            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                MarketEntity entity = marketRepository.findBySymbol(object.getString("acronym"));
                if(entity != null){
                    entity.setCode(object.getString("mic"));
                    entity.setSymbol(object.getString("acronym"));
                    entity.setName(object.getString("name"));
                    entity.setCountry(object.getString("country"));
                    entity.setWebsite(object.getString("website"));

                    response.add(marketRepository.save(entity));
                }else{
                    response.add(marketRepository.save(
                            MarketEntity.builder().
                                    code(object.getString("mic")).
                                    country(object.getString("country")).
                                    symbol(object.getString("acronym")).
                                    name(object.getString("name")).
                                    website(object.getString("website"))
                            .build()));
                }

            }


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<MarketEntity>> getAllMarkets() {
       return ResponseEntity.ok(marketRepository.findAll());
    }
}
