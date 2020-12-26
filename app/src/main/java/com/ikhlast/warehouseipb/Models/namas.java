package com.ikhlast.warehouseipb.Models;

import java.io.Serializable;

public class namas implements Serializable {
    private String user;

    public namas(){} //constructor

    public namas(String username){
        this.user = username;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    //buat ngirim data atau nampilin pas diklik
    @Override
    public String toString() {
        return user;
    }
}
