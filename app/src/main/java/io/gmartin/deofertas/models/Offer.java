package io.gmartin.deofertas.models;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Offer implements Serializable {

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

    private byte[] imageBlob;

    @SerializedName("link")
    private String link;

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
        if (imageStr != null && imageStr.length() > 0) {
            return Base64.decode(imageStr, android.util.Base64.DEFAULT);
        } else if (imageBlob != null && imageBlob.length > 0) {
            return getImageBlob();
        } else {
            return null;
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }
}
