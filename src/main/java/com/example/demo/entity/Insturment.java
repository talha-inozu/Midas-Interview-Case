package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "instrument")
@AllArgsConstructor
@NoArgsConstructor
public class Insturment {


    @Id
    private Integer id;

    private String name;

    private String symbol;

    private String custom_name;

    private String sanane;

}
