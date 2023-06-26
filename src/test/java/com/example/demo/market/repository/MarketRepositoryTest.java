package com.example.demo.market.repository;

import com.example.demo.instrument.entity.InstrumentEntity;
import com.example.demo.instrument.repository.InstrumentRepository;
import com.example.demo.market.entity.MarketEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MarketRepositoryTest {


    @Autowired
    private MarketRepository underTest;


    @Test
    void itShouldCheckIfMarketExist() {
        MarketEntity marketEntity = MarketEntity.builder().id(1000L).code("TEST").build();
        underTest.save(marketEntity);
        MarketEntity expected =underTest.findMarketByCode("TEST");
        assertThat(expected != null).isTrue();

    }

    @Test
    void itShouldCheckIfMarketDoesNotExist() {
        MarketEntity expected =underTest.findMarketByCode("TEST");
        assertThat(expected != null).isFalse();

    }
}