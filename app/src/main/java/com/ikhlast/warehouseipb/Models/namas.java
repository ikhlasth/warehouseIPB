package com.ikhlast.warehouseipb.Models;

import androidx.annotation.Nullable;

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

    //harus ada buat bandingin iterasi
    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;
        if (obj == null || obj.getClass() != getClass()) {
            result = false;
        } else {
            namas n = (namas) obj;
            if (this.user.equals(n.getUser())) {
                result = true;
            }
        }
        return result;
    }

}
