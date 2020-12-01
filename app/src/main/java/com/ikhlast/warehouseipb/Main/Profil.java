package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ikhlast.warehouseipb.R;

public class Profil extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(2).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_menu:
                startActivity(new Intent(Profil.this, Home.class));
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.test_menu:
                startActivity(new Intent(Profil.this, Promo.class));
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.info_menu:
                break;
        }
        return true;
    }
}
