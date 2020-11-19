package com.ikhlast.warehouseipb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Daftar extends AppCompatActivity implements View.OnClickListener {
    EditText username, password, password2;
    Button masuk, daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        masuk = findViewById(R.id.masuk);
        daftar = findViewById(R.id.daftar);

        daftar.setOnClickListener(this);
        masuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.daftar:
                break;
            case R.id.masuk:
                startActivity(new Intent(Daftar.this, Login.class));
                overridePendingTransition(0,0);
                finish();
                break;
        }
    }
}
