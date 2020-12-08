package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ikhlast.warehouseipb.R;

public class Titip extends AppCompatActivity {
    DatabaseReference db;
    StorageReference dbStorage;
    TextView tvKapasitas;
    int capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titip);

        db = FirebaseDatabase.getInstance().getReference();
        dbStorage = FirebaseStorage.getInstance().getReference();
        tvKapasitas = findViewById(R.id.titip_kapasitas);
        checkStorage();
    }
    private void checkStorage(){
        db.child("Preferences").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                capacity = snapshot.child("kapasitas").getValue(Integer.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvKapasitas.setText(Html.fromHtml("Kapasitas gudang tersedia: "+capacity+" m<sup>3</sup>", Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvKapasitas.setText(Html.fromHtml("Kapasitas gudang tersedia: "+capacity+" m<sup>3</sup>"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }
}
