package com.example.currencyconverter.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String currency;

    @Column(unique = true)
    private String code;

    private double ask;

    private double bid;

    public Currency() {
    }

    public Currency(String currency, String code, double ask, double bid) {
        this.currency = currency;
        this.code = code;
        this.ask = ask;
        this.bid = bid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", ask=" + ask +
                ", bid=" + bid +
                '}';
    }
}
