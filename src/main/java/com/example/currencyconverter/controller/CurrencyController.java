package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Currency> currencies = currencyService.getAll();
        model.addAttribute("currencies", currencies);
        return "index";
    }

//    @PostMapping("/result")

}
