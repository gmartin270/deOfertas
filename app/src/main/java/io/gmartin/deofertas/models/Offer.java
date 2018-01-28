package io.gmartin.deofertas.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {

    @SerializedName("id")
    private long id;

    @SerializedName("hash_id")
    private String hashId;

    @SerializedName("desc")
    private String desc;

    @SerializedName("store_id")
    private String storeId;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("price")
    private double price;

    @SerializedName("favorite")
    private boolean favorite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
