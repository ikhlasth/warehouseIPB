package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikhlast.warehouseipb.Fragment.HomeFragment;
import com.ikhlast.warehouseipb.Fragment.InfoFragment;
import com.ikhlast.warehouseipb.Fragment.PromoFragment;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick;
    private Button titip;
    private ProgressDialog loading;

    BottomNavigationView bnv;


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

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(1).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);
//
//        titip = findViewById(R.id.home_tb_titip);
//        titip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loading = ProgressDialog.show(Home.this,
//                        null,
//                        "Mengecek biodata...",
//                        true,
//                        false);
//                check();
//            }
//        });

//    }
//    private void check(){
//        database.child("user").child(user.getUid()).child("Foto KTP").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    startActivity(new Intent(Home.this, Titip.class));
//                    overridePendingTransition(0,0);
//                    loading.dismiss();
//                } else {
//                    startActivity(new Intent(Home.this, Biodata.class));
//                    overridePendingTransition(0,0);
//                    loading.dismiss();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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
                overridePendingTransition(0,0);
                finish();
                break;
        }
        return true;
    }
}
