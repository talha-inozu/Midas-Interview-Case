package com.example.demo.instrument.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "instrument")
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String symbol;

    private String custom_name;

    private String sanane;

}
