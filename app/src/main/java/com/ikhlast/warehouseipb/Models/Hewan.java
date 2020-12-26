package com.ikhlast.warehouseipb.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Hewan implements Serializable {
    private String jenis, penyakit, makanan, vaksin, catatan;

    public Hewan(){} //constructor

    public Hewan(String s1, String s2, String s3, String s4,@Nullable String s5){
        this.jenis = s1;
        this.penyakit = s2;
        this.makanan = s3;
        this.vaksin = s4;
        this.catatan = s5;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    public String getMakanan() {
        return makanan;
    }

    public void setMakanan(String makanan) {
        this.makanan = makanan;
    }

    public String getVaksin() {
        return vaksin;
    }

    public void setVaksin(String vaksin) {
        this.vaksin = vaksin;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    //buat ngirim data atau nampilin pas diklik
    @Override
    public String toString() {
        return " "+jenis+"\n"+" "+penyakit+"\n"+" "+makanan+"\n"+" "+vaksin+"\n"+" "+catatan;
    }
}
