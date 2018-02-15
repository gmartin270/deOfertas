package io.gmartin.deofertas.models;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class OfferImage {

    @SerializedName("id")
    private Long id;

    @SerializedName("image")
    private String imageStr;

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
        return Base64.decode(imageStr, android.util.Base64.DEFAULT);
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }
}