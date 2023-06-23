package com.example.demo.service;

import com.example.demo.entity.Insturment;
import com.example.demo.repository.InsturmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InsturmetnServiceImpl implements InsturmentService{


    @Autowired
    InsturmentRepository insturmentRepository;

    @Override
    public Insturment findBySymbol(String symbol) {
        return insturmentRepository.findBySymbol(symbol);
    }


}
