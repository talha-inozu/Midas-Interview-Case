package com.example.demo.instrument.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "instrument")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstrumentEntity {


    @Id
    private Long id;

    private String name;

    private String symbol;

    private String custom_name;

    private Long market_id;


}
