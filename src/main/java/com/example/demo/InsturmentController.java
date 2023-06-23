package com.example.demo;

import com.example.demo.entity.Insturment;
import com.example.demo.service.InsturmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insturments")
public class InsturmentController {

    @Autowired
    InsturmentService insturmentService;


    @GetMapping("/{symbol}")
    public Insturment getBySymbol(@PathVariable String symbol){
        return insturmentService.findBySymbol(symbol);
    }

}
