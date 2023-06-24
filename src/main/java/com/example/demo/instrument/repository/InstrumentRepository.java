package com.example.demo.instrument.repository;

import com.example.demo.instrument.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentEntity,Long>{

    @Query("select i from InstrumentEntity i where i.symbol = ?1")
    InstrumentEntity findBySymbol(String symbol);

    @Query("SELECT MAX(id) FROM InstrumentEntity")
    Long findLastId();

}