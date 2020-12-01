package com.ikhlast.warehouseipb.Models;

import java.io.Serializable;

public class ModelPaket implements Serializable {
    private String judul, desc;
    private int dp;

    public ModelPaket(){} //constructor

    public ModelPaket(String judul, String desc, int dp){
        this.judul = judul;
        this.desc = desc;
        this.dp = dp;
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

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    //buat ngirim data atau nampilin pas diklik
    @Override
    public String toString() {
        return " "+judul+"\n"+" "+desc+"\n"+" "+dp;
    }
}
