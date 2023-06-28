package com.example.demo.market.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.service.InstrumentServiceImpl;
import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.repository.MarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class MarketServiceImplTest {

    @Mock
    MarketRepository marketRepository;

    @Mock
    RestTemplate restTemplate;


    MarketService underTest;
    @BeforeEach
    void setUp(){
        underTest = new MarketServiceImpl(marketRepository,restTemplate);
    }
    @Test
    void syncMarket() {
        String responseBody = "{\n" +
                "    \"next\": null,\n" +
                "    \"previous\": null,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"url\": \"https://api.robinhood.com/markets/IEXG/\",\n" +
                "            \"todays_hours\": \"https://api.robinhood.com/markets/IEXG/hours/2023-06-26/\",\n" +
                "            \"mic\": \"IEXG\",\n" +
                "            \"operating_mic\": \"IEXG\",\n" +
                "            \"acronym\": \"IEX\",\n" +
                "            \"name\": \"IEX Market\",\n" +
                "            \"city\": \"New York\",\n" +
                "            \"country\": \"US - United States of America\",\n" +
                "            \"timezone\": \"US/Eastern\",\n" +
                "            \"website\": \"www.iextrading.com\"\n" +
                "        },\n" +
                "    ]\n" +
                "}";

        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok(responseBody);
        when(restTemplate.exchange(anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);


        MarketEntity marketEntityForFind = MarketEntity.builder().id(1L).code("IEXG").build();
        when(marketRepository.findMarketByCode(anyString())).thenReturn(marketEntityForFind);


        MarketEntity marketEntityForSave = MarketEntity.builder().
                code("IEXG").
                country("US - United States of America").
                symbol("IEX").
                name("IEX Market").
                website("www.iextrading.com")
                .build();
        when(marketRepository.save(any(MarketEntity.class))).thenReturn(marketEntityForSave);



        ResponseEntity<List<MarketEntity>> response = underTest.syncMarket();


        verify(restTemplate).exchange(anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.eq(String.class));
        verify(marketRepository).findMarketByCode(anyString());
        verify(marketRepository).save(any(MarketEntity.class));
        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody().size() == 1);
    }

    @Test
    void findMarketByCode() {
        MarketEntity expectedEntity = MarketEntity.builder().id(1000L).code("TEST").build();
        when(marketRepository.findMarketByCode("TEST")).thenReturn(expectedEntity);

        MarketEntity resultEntity = underTest.findMarketByCode("TEST");
        assertThat(resultEntity).isEqualTo(expectedEntity);
    }
}