package com.xafarr;

import com.opencsv.bean.CsvBindByName;

public class Event {
    @CsvBindByName(column = "buyer_party")
    private String buyer;
    @CsvBindByName(column = "seller_party")
    private String seller;
    @CsvBindByName(column = "premium_currency")
    private String currency;
    @CsvBindByName(column = "premium_amount")
    private Double amount;

    public Event() {
    }

    public Event(String buyer, String seller, String currency, Double amount) {
        this.buyer = buyer;
        this.seller = seller;
        this.currency = currency;
        this.amount = amount;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Event{" +
                "buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
