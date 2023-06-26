package com.example.demo.instrument.repository;

import com.example.demo.instrument.data.InstrumentsDataInitializer;
import com.example.demo.instrument.entity.InstrumentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;

import javax.sound.midi.Instrument;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class InstrumentRepositoryTest {

    @Autowired
    private InstrumentRepository underTest;

    @Test
    void itShouldCheckIfInstrumentExist() {
        InstrumentEntity instrumentEntity = InstrumentEntity.builder().id(1000L).symbol("TEST").build();
        underTest.save(instrumentEntity);
        InstrumentEntity expected =underTest.findInstrumentBySymbol("TEST");
        assertThat(expected != null).isTrue();

    }

    @Test
    void itShouldCheckIfInstrumentDoesNotExist() {
        InstrumentEntity expected =underTest.findInstrumentBySymbol("TEST");
        assertThat(expected != null).isFalse();

    }
}