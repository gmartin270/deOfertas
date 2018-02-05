package io.gmartin.deofertas.models;

import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    private long id;

    @SerializedName("business_name")
    private String name;

    @SerializedName("logo")
    private byte[] logo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
