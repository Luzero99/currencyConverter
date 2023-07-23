package com.example.currencyconverter.controller;

import com.example.currencyconverter.dto.CurrencyConvertDTO;
import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DecimalFormat;

@Controller
public class CurrencyController {
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("currencies", currencyService.getAll());
        return "index";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute CurrencyConvertDTO request, Model model) {
        Currency currencyFrom = currencyService.findOne(request.getFrom());
        Currency currencyTo = currencyService.findOne(request.getTo());
        double rate = currencyFrom.getBid() / currencyTo.getBid();
        double result = request.getAmount() * rate;

        model.addAttribute("amount", df.format(request.getAmount()));
        model.addAttribute("from", request.getFrom());
        model.addAttribute("to", request.getTo());
        model.addAttribute("rate", df.format(rate));
        model.addAttribute("result", df.format(result));
        model.addAttribute("currencies", currencyService.getAll());
        return "index";
    }
}
