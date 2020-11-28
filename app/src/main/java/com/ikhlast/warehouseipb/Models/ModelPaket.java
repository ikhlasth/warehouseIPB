package com.ikhlast.warehouseipb.Models;

import java.io.Serializable;

public class ModelPaket implements Serializable {
    private String url, judul, desc, dp;

    public ModelPaket(){} //constructor

    public ModelPaket(String url, String judul, String desc, String dp){
        this.url = url;
        this.judul = judul;
        this.desc = desc;
        this.dp = dp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}
