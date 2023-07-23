package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CurrencyService(WebClient.Builder webClientBuilder, CurrencyRepository currencyRepository) {
        this.webClientBuilder = webClientBuilder;
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 9 * * ?")
    public void fetchData() {
        String apiUrl = "http://api.nbp.pl/api/exchangerates/tables/c/";
        WebClient webClient = webClientBuilder.baseUrl(apiUrl).build();

        String responseBody = webClient.get().retrieve().bodyToMono(String.class).block();

        if (responseBody != null) {
            List<Currency> currencies = parseCurrencyDataFromApiResponse(responseBody);
            saveOrUpdateCurrencies(currencies);
        }

        System.out.println("Response Body: " + responseBody);
    }

    private void saveOrUpdateCurrencies(List<Currency> currencies) {
        for (Currency currency : currencies) {
            Currency existingCurrency = currencyRepository.findByCode(currency.getCode());

            if (existingCurrency != null) {
                existingCurrency.setAsk(currency.getAsk());
                existingCurrency.setBid(currency.getBid());
                currencyRepository.save(existingCurrency);
            } else {
                currencyRepository.save(currency);
            }
        }
    }

    private List<Currency> parseCurrencyDataFromApiResponse(String responseBody) {
        List <Currency> currencies = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String response = responseBody.substring(responseBody.indexOf("[{\"currency\":"), responseBody.length() - 1);

            System.out.println(response);

            currencies = objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currencies;
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    public Currency findOne(String code) {
        return currencyRepository.findByCode(code);
    }
}
