package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ikhlast.warehouseipb.Fragment.TitipBarangFragment;
import com.ikhlast.warehouseipb.Fragment.TitipHewanFragment;
import com.ikhlast.warehouseipb.Fragment.TitipKeduanyaFragment;
import com.ikhlast.warehouseipb.R;

//TODO UPDATE LAYOUT, FAB TITIP BARANG DAN KEDUANYA
public class Titip extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference db;
    StorageReference dbStorage;
    TextView tvKapasitas;
    int capacity;

    Boolean FH = false, FB = false, FHB = false;
    FloatingActionButton fab;
    Button hewan, barang, keduanya;
    AlertDialog.Builder alert;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titip);

        db = FirebaseDatabase.getInstance().getReference();
        dbStorage = FirebaseStorage.getInstance().getReference();
        tvKapasitas = findViewById(R.id.titip_kapasitas);
        checkStorage();

        fab = findViewById(R.id.titip_fab);
        hewan = findViewById(R.id.titip_bt_hewan);
        barang = findViewById(R.id.titip_bt_barang);
        keduanya = findViewById(R.id.titip_bt_keduanya);

        fab.setOnClickListener(this);
        hewan.setOnClickListener(this);
        barang.setOnClickListener(this);
        keduanya.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titip_bt_barang:
                FB = true;
                FH = false;
                FHB = false;
                hewan.setBackgroundResource(R.drawable.tombol2);
                keduanya.setBackgroundResource(R.drawable.tombol2);
                barang.setBackgroundResource(R.drawable.tombol);
                barang.setTextColor(Color.parseColor("#ffffff"));
                hewan.setTextColor(Color.parseColor("#7e9f82"));
                keduanya.setTextColor(Color.parseColor("#7e9f82"));
                loadFrag(new TitipBarangFragment());
                break;
            case R.id.titip_bt_hewan:
                FH = true;
                FB = false;
                FHB = false;
                barang.setBackgroundResource(R.drawable.tombol2);
                keduanya.setBackgroundResource(R.drawable.tombol2);
                hewan.setBackgroundResource(R.drawable.tombol);
                hewan.setTextColor(Color.parseColor("#ffffff"));
                keduanya.setTextColor(Color.parseColor("#7e9f82"));
                barang.setTextColor(Color.parseColor("#7e9f82"));
                loadFrag(new TitipHewanFragment());
                break;
            case R.id.titip_bt_keduanya:
                FHB = true;
                FH = false;
                FB = false;
                hewan.setBackgroundResource(R.drawable.tombol2);
                barang.setBackgroundResource(R.drawable.tombol2);
                keduanya.setBackgroundResource(R.drawable.tombol);
                keduanya.setTextColor(Color.parseColor("#ffffff"));
                hewan.setTextColor(Color.parseColor("#7e9f82"));
                barang.setTextColor(Color.parseColor("#7e9f82"));
                loadFrag(new TitipKeduanyaFragment());
                break;
            case R.id.titip_fab:
                if (FH){
                    fabOnClick(R.layout.helph);
                } else if (FB){
                    fabOnClick(R.layout.helpb);
                } else if (FHB){
                    fabOnClick(R.layout.helphb);
                } else {
                    fabOnClick(R.layout.helpmain);
                }
                break;
        }
    }

    private boolean loadFrag(Fragment f){
        if (f != null){
            fm.beginTransaction().replace(R.id.titip_container, f).commit();
            return true;
        }
        return false;
    }
    private void fabOnClick(int l){
        alert = new AlertDialog.Builder(this);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pastel_ijomuda)));
        alert.setView(l).setTitle("Keterangan").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            }
        }).create().show();
    }
}
