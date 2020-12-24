package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikhlast.warehouseipb.Adapter.AdapterAdmin1;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class Admin extends AppCompatActivity implements AdapterAdmin1.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db, dbStat;
    private RecyclerView rv1, rv2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm1, lm2;
    private ArrayList<ModelPaket> daftarProfil;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user, statusPenitipan, berlakuSampai;

    private TextView greeting, stat;
    private Button edit;

    AlertDialog.Builder alert;
    Sessions sessions;

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");

        bnv = findViewById(R.id.nav_homeadmin);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);

        rv1 = (RecyclerView) findViewById(R.id.homeadmin_rv1);
        rv2 = (RecyclerView) findViewById(R.id.homeadmin_rv2);
        rv1.setHasFixedSize(true);
        rv2.setHasFixedSize(true);
        lm1 = new LinearLayoutManager(this);
        lm2 = new LinearLayoutManager(this);
        rv1.setLayoutManager(lm1);
        rv2.setLayoutManager(lm2);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onDataClick(ModelPaket barang, int position) {

    }
}
