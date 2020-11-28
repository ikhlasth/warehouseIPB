package com.ikhlast.warehouseipb.Launch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ikhlast.warehouseipb.R;
import com.ikhlast.warehouseipb.Preferences.Sessions;

public class SplashActivity extends AppCompatActivity {
    private int delay = 2000;
    public static int jmlsoal, jmlsoaldummy, jmlsoalpg, jmlsoalpgdummy, jmlsoalbs, jmlsoalbsdummy, pbenar, pbenarbs, psalah, psalahbs, pkosong, timlolos, waktu;
    public static String bnow, bnext, jkosong, sesi, spass, spassdummy, benar, salah, cp;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    Sessions session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        session = new Sessions(getApplicationContext());

        try {
            new Handler(Looper.myLooper()).postDelayed(new Runnable(){
                @Override
                public void run(){

                    session.checkLogin();
//                    startActivity(new Intent(SplashActivity.this, Login.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            },delay);
            } catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }

    }
}
