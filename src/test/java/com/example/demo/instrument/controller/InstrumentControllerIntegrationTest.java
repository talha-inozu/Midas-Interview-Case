package com.example.demo.instrument.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.isA;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Transactional
class InstrumentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void teardown() {
        entityManager.clear();
        entityManager.close();
    }

    @Test
    void canGetInstrumentBySymbol() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/instruments/?symbol=AAPL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value("AAPL"))
                .andReturn();
    }

    @Test
    void canGetAllInstruments() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/instruments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].symbol").value("AAPL"))
                .andReturn();
    }

    @Test
    void canSyncInstruments() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/instruments/sync"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].symbol").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].custom_name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].market_id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].market_id").value(isA(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn();

    }
}