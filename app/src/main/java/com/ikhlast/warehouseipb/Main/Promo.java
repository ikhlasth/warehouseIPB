package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterPromo;
import com.ikhlast.warehouseipb.Fragment.HomeFragment;
import com.ikhlast.warehouseipb.Fragment.InfoFragment;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class Promo extends AppCompatActivity implements AdapterPromo.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ModelPaket> daftarPromo;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user;

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");

        rv = (RecyclerView) findViewById(R.id.recycler_list);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        db = FirebaseDatabase.getInstance().getReference();

        loading = ProgressDialog.show(Promo.this,
                null,
                "Harap tunggu...",
                true,
                false);

        db.child("Promo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarPromo = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()){
                    ModelPaket barang = note.getValue(ModelPaket.class);
                    barang.setJudul(note.getKey());
                    daftarPromo.add(barang);
                }
                adapter = new AdapterPromo(daftarPromo, Promo.this);
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

    public void onPromoClick(ModelPaket barang, final int position){
        if (db != null){
            Toast.makeText(Promo.this, "Anda mengklik paket "+barang, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_menu:
                startActivity(new Intent(Promo.this, Home.class));
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.test_menu:
                break;
            case R.id.info_menu:
                startActivity(new Intent(Promo.this, Profil.class));
                overridePendingTransition(0,0);
                finish();
                break;
        }
        return true;
    }
}
