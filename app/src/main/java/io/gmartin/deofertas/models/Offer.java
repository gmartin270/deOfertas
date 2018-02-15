package io.gmartin.deofertas.models;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("id")
    private Long id;

    @SerializedName("hash_id")
    private String hashId;

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("store_id")
    private Long storeId;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("price")
    private double price;

    @SerializedName("favorite")
    private boolean favorite;

    @SerializedName("image")
    private String imageStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageStr() {
        return imageStr;
    }

    public byte[] getImage() {
        return Base64.decode(imageStr, android.util.Base64.DEFAULT);
    }
}
