package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterProfil;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class Profil extends AppCompatActivity implements AdapterProfil.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db, dbStat;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ModelPaket> daftarProfil;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user, statusPenitipan, berlakuSampai;

    private TextView greeting, stat;

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(2).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");

        rv = (RecyclerView) findViewById(R.id.profil_recycler_list);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);


        db = FirebaseDatabase.getInstance().getReference();
        dbStat = FirebaseDatabase.getInstance().getReference("user").child(mUser.getUid()).child("status penitipan");

        greeting = findViewById(R.id.profil_nama);
        stat = findViewById(R.id.profil_status);

        getStatusPenitipan();
        greeting.setText("Hi, "+user);
        loading = ProgressDialog.show(Profil.this,
                null,
                "Harap tunggu...",
                true,
                false);

        getRiwayatData();

    }
    private void getStatusPenitipan(){
        dbStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                statusPenitipan = snapshot.child("nama").getValue(String.class);
                berlakuSampai = snapshot.child("berakhir").getValue(String.class);
                } else {
                    statusPenitipan = "Anda belum menitipkan apapun";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stat.setText(Html.fromHtml("<b>Anda memilih: </b>"+statusPenitipan+"<br></br><b>Berlaku sampai: </b>"+berlakuSampai, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    stat.setText(Html.fromHtml("<b>Anda memilih: </b>"+statusPenitipan+"<br></br><b>Berlaku sampai: </b>"+berlakuSampai));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRiwayatData(){
        db.child("user").child(mUser.getUid()).child("riwayat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarProfil = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()){
                    ModelPaket barang = note.getValue(ModelPaket.class);
                    barang.setJudul(note.getKey());
                    daftarProfil.add(barang);
                }
                adapter = new AdapterProfil(daftarProfil, Profil.this);
                rv.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println(error.getDetails()+" "+error.getMessage());
                loading.dismiss();
            }
        });
    }


    @Override
    public void onRiwayatClick(ModelPaket barang, final int position){
        if (db != null){
            Toast.makeText(Profil.this, "Anda mengklik riwayat "+barang, Toast.LENGTH_LONG).show();
        }
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
