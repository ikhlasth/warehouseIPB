package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;
import com.ikhlast.warehouseipb.TestLab.DynamicTester2;
import com.ikhlast.warehouseipb.TestLab.TestDynamic;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick;
    private Button titip;
    private TextView tvBelum;
    private ProgressDialog loading;

    BottomNavigationView bnv;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        alert = new AlertDialog.Builder(this);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sessions = new Sessions(getApplicationContext());
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com","");

        tvBelum = findViewById(R.id.beranda_teksbelumnitip);
        //todo benerin kalo beneran
//        dbCheck();

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(1).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);

        titip = findViewById(R.id.home_tb_titip);
        titip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(Home.this,
                        null,
                        "Mengecek biodata...",
                        true,
                        false);
                check();
            }
        });

    }
    private void check(){
        database.child("user").child(user.getUid()).child("Foto KTP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    startActivity(new Intent(Home.this, Titip.class));
                    overridePendingTransition(0,0);
                    loading.dismiss();
                } else {
                    startActivity(new Intent(Home.this, Biodata.class));
                    overridePendingTransition(0,0);
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void dbCheck(){
        database.child("List/Sedang Berjalan/id/"+user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tvBelum.setVisibility(View.GONE);
                    rv = findViewById(R.id.beranda_recycler_list);
                    rv.setVisibility(View.VISIBLE);
                    rv.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getWindow().getContext());
                    rv.setLayoutManager(layoutManager);
                } else {
                    tvBelum.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
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
        logout();
    }


    public void logout(){
        alert
                .setTitle("Keluar")
                .setMessage("Apakah anda ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.child("sedangAktif").child(nick).removeValue();
                        sessions.logoutUser();
                        mAuth.signOut();
                        finish();
                        overridePendingTransition(0,0);
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home_menu:
                break;
            case R.id.test_menu:
                startActivity(new Intent(Home.this, Promo.class));
                overridePendingTransition(0,0);
                finish();
                break;
            case R.id.info_menu:
                startActivity(new Intent(Home.this, Profil.class));
//                startActivity(new Intent(Home.this, DynamicTester2.class));
                overridePendingTransition(0,0);
                finish();
                break;
        }
        return true;
    }
}
