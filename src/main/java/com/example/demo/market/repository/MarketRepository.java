package com.example.demo.market.repository;


import com.example.demo.market.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<MarketEntity,Long> {

    @Query("select i from MarketEntity i where i.symbol = ?1")
    MarketEntity findBySymbol(String symbol);
}
