package com.example.demo.repository;

import com.example.demo.entity.Insturment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InsturmentRepository extends JpaRepository<Insturment,Integer>{

    @Query("select i from Insturment i where i.symbol = ?1")
    Insturment findBySymbol(String symbol);

//    @Transactional
//    @Modifying
//    @Query(value = "ALTER TABLE instrument ADD name VARCHAR(50) ;ALTER TABLE instrument ADD custom_name VARCHAR(50);",nativeQuery = true)
//    void addColumn();

//    @Transactional
//    @Modifying
//    @Query(value = "ALTER TABLE instrument ADD custom_name VARCHAR(50)",nativeQuery = true)
//    void addColumn2();

}