package com.ikhlast.warehouseipb.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.ikhlast.warehouseipb.Main.Admin;
import com.ikhlast.warehouseipb.Main.Home;
import com.ikhlast.warehouseipb.Credentials.Login;
import com.ikhlast.warehouseipb.Launch.Welcome;

import java.util.HashMap;

public class Sessions {
    SharedPreferences spf;
    SharedPreferences.Editor editor;
    Context ctx;

    //spf mode
    int PRIVATE_MODE = 0;
    // Sharedspf file name
    private static final String PREF_NAME = "warehousePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    //First time
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    // Constructor
    public Sessions(Context context) {
        this.ctx = context;
        spf = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = spf.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in spf
        editor.putString(KEY_NAME, name);

        // Storing nick in spf
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If true it will redirect user to home page
     * Else won't do anything
     * */
    public void checkLogin(){
        if(this.isLoggedIn()) {
            // user is logged in redirect him to Home Activity
            if (spf.getString(KEY_NAME, null).equals("adminwhipb")){
                Intent j = new Intent(ctx, Admin.class);
                        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        j.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(j);
            } else {
                Intent i = new Intent(ctx, Home.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Home Activity
                ctx.startActivity(i);
            }
        } else {
            Intent start=new Intent(ctx, Welcome.class);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(start);
        }
        // Check login status
//        if(!this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(ctx, Login.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            ctx.startActivity(i);
//        }
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, spf.getString(KEY_NAME, null));

        // user nick id
        user.put(KEY_EMAIL, spf.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(ctx, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        ctx.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return spf.getBoolean(IS_LOGIN, false);
    }
}
