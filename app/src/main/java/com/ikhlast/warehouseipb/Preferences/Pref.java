package com.ikhlast.warehouseipb.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ikhlast.warehouseipb.launch.Welcome;

import java.util.HashMap;

public class Pref {
    SharedPreferences spf;
    SharedPreferences.Editor editor;
    Context ctx;

    //spf mode
    int PRIVATE_MODE = 0;
    // Sharedspf file name
    private static final String PREF_NAME = "warehousePrefFirst";

    //First time
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Constructor
    public Pref(Context context) {
        this.ctx = context;
        spf = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = spf.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return spf.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
