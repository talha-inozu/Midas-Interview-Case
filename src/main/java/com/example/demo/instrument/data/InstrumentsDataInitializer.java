package com.example.demo.instrument.data;
import com.example.demo.instrument.service.InstrumentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
@Component
@AllArgsConstructor
public class InstrumentsDataInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InstrumentsDataInitializer.class);
    @Autowired
    private final InstrumentService instrumentService;
    @Autowired
    private final DataSource dataSource;
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(instrumentService.getAllInstruments().getBody().size()<1){
            log.info("Instruments data initialization is started");
            String sqlFilePath = "classpath:seed.sql";

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceLoader.getResource(sqlFilePath).getInputStream()))) {
                String line;
                StringBuilder sqlStatements = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sqlStatements.append(line);
                    if (line.endsWith(";")) {
                        jdbcTemplate.execute(sqlStatements.toString());
                        sqlStatements.setLength(0);
                    }
                }
            }catch (Exception e){
                log.error("ERROR at InstrumentsDataInitializer : " + e.getMessage());
            }

            log.info("Instruments data initialization is completed");
        }
    }
}