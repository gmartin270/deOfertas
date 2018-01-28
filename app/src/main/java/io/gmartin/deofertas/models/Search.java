package io.gmartin.deofertas.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Search implements Serializable{
    private long id;

    @SerializedName("text")
    private String text;

    @SerializedName("stores")
    private List<Store> stores;

    @SerializedName("price_from")
    private double priceFrom;

    @SerializedName("price_to")
    private double priceTo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }
}
