package io.gmartin.deofertas.models;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class SuggestedOffer implements Serializable{

    @SerializedName("id")
    private Long id;

    @SerializedName("offer_id")
    private Long offer_id;

    @SerializedName("suggestion_date_str")
    private Date suggestionDate;

    @SerializedName("suggested_image_str")
    private String imageStr;

    @SerializedName("offer")
    private Offer offer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageStr() {
        return imageStr;
    }

    public byte[] getImage() {
        return Base64.decode(imageStr, Base64.DEFAULT);
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public Long getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Long offer_id) {
        this.offer_id = offer_id;
    }

    public Date getSuggestionDate() {
        return suggestionDate;
    }

    public void setSuggestionDate(Date suggestionDate) {
        this.suggestionDate = suggestionDate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}