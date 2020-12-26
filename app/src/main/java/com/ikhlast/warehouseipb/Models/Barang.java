package com.ikhlast.warehouseipb.Models;

import java.io.Serializable;

public class Barang implements Serializable {
    private String nama, jumlah;

    public Barang(){} //constructor

    public Barang(String s1, String s2){
        this.nama = s1;
        this.jumlah = s2;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    //buat ngirim data atau nampilin pas diklik
    @Override
    public String toString() {
        return " "+nama+"\n"+" "+jumlah;
    }
}
