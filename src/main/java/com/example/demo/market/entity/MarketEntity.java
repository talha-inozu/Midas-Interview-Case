package com.example.demo.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "markets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String symbol;

    private String name;
    private String country;

    private String website;


}
