package com.ikhlast.warehouseipb.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ikhlast.warehouseipb.R;

public class Titip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titip);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }
}
