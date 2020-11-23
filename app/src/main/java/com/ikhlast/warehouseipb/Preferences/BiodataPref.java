package com.ikhlast.warehouseipb.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ikhlast.warehouseipb.Credentials.Login;
import com.ikhlast.warehouseipb.Main.Admin;
import com.ikhlast.warehouseipb.Main.Home;
import com.ikhlast.warehouseipb.launch.Welcome;

import java.util.HashMap;

public class BiodataPref {
    SharedPreferences spf;
    SharedPreferences.Editor editor;
    Context ctx;

    //spf mode
    int PRIVATE_MODE = 0;
    // Sharedspf file name
    private static final String PREF_NAME = "biodataPref";

    // All Shared Preferences Keys
    private static final String IS_FIRST_REG = "IsFirstReg";


    // Constructor
    public BiodataPref(Context context) {
        this.ctx = context;
        spf = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = spf.edit();
    }

    public void setFirstTimeRegister(boolean isFirstReg){
        editor.putBoolean(IS_FIRST_REG, isFirstReg);
        editor.commit();
    }

    public boolean isRegistered(){
        return spf.getBoolean(IS_FIRST_REG, false);
    }
}
