package com.example.demo.instrument.service;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.repository.InstrumentRepository;
import com.example.demo.market.entity.MarketEntity;
import com.example.demo.market.service.MarketService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
class InstrumentServiceImplTest {

    @Mock
    private InstrumentRepository instrumentRepository;
    @Mock
    private MarketService marketService;
    @Mock
    private RestTemplate restTemplate;
    private InstrumentService underTest;


    @BeforeEach
    void setUp(){
        underTest = new InstrumentServiceImpl(instrumentRepository,restTemplate,marketService);
    }

    @Test
    void canFindInstrumentBySymbol() {
        InstrumentEntity expectedEntity = InstrumentEntity.builder().id(1000L).symbol("TEST").build();
        when(instrumentRepository.findInstrumentBySymbol("TEST")).thenReturn(expectedEntity);

        InstrumentEntity resultEntity = underTest.findInstrumentBySymbol("TEST").getBody();

        assertThat(resultEntity).isEqualTo(expectedEntity);
    }

    @Test
    void canGetAllInstruments() {
        underTest.getAllInstruments();
        verify(instrumentRepository).findAll();
    }

    @Test
    void canSyncInstruments() {

        List<InstrumentEntity> instrumentEntityList = new ArrayList<>();
        InstrumentEntity instrumentEntity = InstrumentEntity.builder().id(1L).symbol("AAPL").build();
        instrumentEntityList.add(instrumentEntity);
        when(instrumentRepository.findAll()).thenReturn(instrumentEntityList);
        String responseBody = "{\n" +
                "    \"next\": null,\n" +
                "    \"previous\": null,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"450dfc6d-5510-4d40-abfb-f633b7d9be3e\",\n" +
                "            \"url\": \"https://api.robinhood.com/instruments/450dfc6d-5510-4d40-abfb-f633b7d9be3e/\",\n" +
                "            \"quote\": \"https://api.robinhood.com/quotes/AAPL/\",\n" +
                "            \"fundamentals\": \"https://api.robinhood.com/fundamentals/AAPL/\",\n" +
                "            \"splits\": \"https://api.robinhood.com/instruments/450dfc6d-5510-4d40-abfb-f633b7d9be3e/splits/\",\n" +
                "            \"state\": \"active\",\n" +
                "            \"market\": \"https://api.robinhood.com/markets/XNAS/\",\n" +
                "            \"simple_name\": \"Apple\",\n" +
                "            \"name\": \"Apple Inc. Common Stock\",\n" +
                "            \"tradeable\": true,\n" +
                "            \"tradability\": \"tradable\",\n" +
                "            \"symbol\": \"AAPL\",\n" +
                "            \"bloomberg_unique\": \"EQ0010169500001000\",\n" +
                "            \"margin_initial_ratio\": \"0.5000\",\n" +
                "            \"maintenance_ratio\": \"0.2500\",\n" +
                "            \"country\": \"US\",\n" +
                "            \"day_trade_ratio\": \"0.2500\",\n" +
                "            \"list_date\": \"1990-01-02\",\n" +
                "            \"min_tick_size\": null,\n" +
                "            \"type\": \"stock\",\n" +
                "            \"tradable_chain_id\": \"7dd906e5-7d4b-4161-a3fe-2c3b62038482\",\n" +
                "            \"rhs_tradability\": \"tradable\",\n" +
                "            \"fractional_tradability\": \"tradable\",\n" +
                "            \"default_collar_fraction\": \"0.05\",\n" +
                "            \"ipo_access_status\": null,\n" +
                "            \"ipo_access_cob_deadline\": null,\n" +
                "            \"ipo_s1_url\": null,\n" +
                "            \"ipo_roadshow_url\": null,\n" +
                "            \"is_spac\": false,\n" +
                "            \"is_test\": false,\n" +
                "            \"ipo_access_supports_dsp\": false,\n" +
                "            \"extended_hours_fractional_tradability\": true,\n" +
                "            \"internal_halt_reason\": \"\",\n" +
                "            \"internal_halt_details\": \"\",\n" +
                "            \"internal_halt_sessions\": null,\n" +
                "            \"internal_halt_start_time\": null,\n" +
                "            \"internal_halt_end_time\": null,\n" +
                "            \"internal_halt_source\": \"\",\n" +
                "            \"all_day_tradability\": \"tradable\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok(responseBody);
        when(restTemplate.exchange(anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);
        MarketEntity marketEntity = MarketEntity.builder().id(1L).code("XNAS").build();
        when(marketService.findMarketByCode(anyString())).thenReturn(marketEntity);

        InstrumentEntity savedInstrumentEntity = InstrumentEntity.builder().market_id(1L).id(1L).symbol("AAPL").build();
        when(instrumentRepository.save(any(InstrumentEntity.class))).thenReturn(savedInstrumentEntity);

        ResponseEntity<List<InstrumentEntity>> response = underTest.syncInstruments();
        verify(instrumentRepository).findAll();
        verify(restTemplate).exchange(anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.eq(null), ArgumentMatchers.eq(String.class));
        verify(marketService).findMarketByCode(anyString());
        verify(instrumentRepository).save(any(InstrumentEntity.class));
        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody().size() == 1);


    }
}