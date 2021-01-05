package com.ikhlast.warehouseipb.Models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class riwayats implements Serializable {
    private String tanggal;

    public riwayats(){} //constructor

    public riwayats(String tanggalname){
        this.tanggal = tanggalname;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    //buat ngirim data atau nampilin pas diklik
    @Override
    public String toString() {
        return tanggal;
    }

    //harus ada buat bandingin iterasi
    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;
        if (obj == null || obj.getClass() != getClass()) {
            result = false;
        } else {
            riwayats n = (riwayats) obj;
            if (this.tanggal.equals(n.getTanggal())) {
                result = true;
            }
        }
        return result;
    }

}
