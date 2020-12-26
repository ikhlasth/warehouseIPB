package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterAdmin1;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.Models.namas;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class Admin extends AppCompatActivity implements AdapterAdmin1.DataListener, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DatabaseReference db, dbStat;
    private RecyclerView rv, rv1, rv2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm1, lm2;
    private ArrayList<namas> daftarKonfirmasi, daftarBerjalan;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user, nama, statusPenitipan, berlakuSampai;
    private EditText etKapasitas, etLainnya;

    private Button confirm, onProgress;

    private AlertDialog.Builder alert;
    private Sessions sessions;

    private BottomNavigationView bnv;
    private ViewPager viewPager;
    private viewAdapter vpAdapter;
    private int[] layouts;
    private LinearLayout lb;
    private View addv;

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

        confirm = findViewById(R.id.admin_btn_menunggu);
        onProgress = findViewById(R.id.admin_btn_onGoing);
        lb = findViewById(R.id.admin_linearbutton);
        confirm.setTextColor(Color.parseColor("#7e9f82"));
        confirm.setOnClickListener(this);
        onProgress.setOnClickListener(this);
        etKapasitas = findViewById(R.id.manage_kapasitas);
        etLainnya = findViewById(R.id.manage_lainnya);

        viewPager = findViewById(R.id.view_pager_admin);
        layouts = new int[]{
                R.layout.admin_menunggukonfirmasi,
                R.layout.admin_sedangberjalan,
                R.layout.manage};

        vpAdapter = new viewAdapter();
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(pagelistener);
        rv = findViewById(R.id.admin_recycler_konfirmasi_sementara);
        rv.setHasFixedSize(true);
        lm1 = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm1);
        getConf1();
        }


    ViewPager.OnPageChangeListener pagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0){
                lb.setVisibility(View.VISIBLE);
                confirm.setTextColor(Color.parseColor("#7e9f82"));
                onProgress.setTextColor(Color.parseColor("#000000"));
                bnv.getMenu().getItem(0).setChecked(true);
                rv1 = findViewById(R.id.admin_recycler_konfirmasi);
                rv1.setHasFixedSize(true);
                lm1 = new LinearLayoutManager(getApplicationContext());
                rv1.setLayoutManager(lm1);
                getConf();
                rv.setVisibility(View.GONE);
            } else if (position == 1){
                lb.setVisibility(View.VISIBLE);
                onProgress.setTextColor(Color.parseColor("#7e9f82"));
                confirm.setTextColor(Color.parseColor("#000000"));
                bnv.getMenu().getItem(0).setChecked(true);
                rv2 = findViewById(R.id.admin_recycler_berjalan);
                rv2.setHasFixedSize(true);
                lm2 = new LinearLayoutManager(getApplicationContext());
                rv2.setLayoutManager(lm2);
                getOngoing();
                rv.setVisibility(View.GONE);
            } else {
                lb.setVisibility(View.GONE);
                bnv.getMenu().getItem(1).setChecked(true);
                rv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void getConf1(){
        loading = ProgressDialog.show(Admin.this,
                null,
                "Harap tunggu...",
                true,
                false);
        db.child("List/Pesanan Masuk/Barang/id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarKonfirmasi = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()) {
//                        ModelPaket barang = note.getValue(ModelPaket.class);
                    namas nama = note.getValue(namas.class);
                    nama.setUser(note.getKey());
                    daftarKonfirmasi.add(nama);
                }
                adapter = new AdapterAdmin1(daftarKonfirmasi, Admin.this);
                rv.setAdapter(adapter);
                loading.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.dismiss();
            }
        });
    }

    private void getConf(){
        loading = ProgressDialog.show(Admin.this,
                null,
                "Harap tunggu...",
                true,
                false);
        db.child("List/Pesanan Masuk/Barang/id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarKonfirmasi = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()) {
//                        ModelPaket barang = note.getValue(ModelPaket.class);
                        namas nama = note.getValue(namas.class);
                        nama.setUser(note.getKey());
                        daftarKonfirmasi.add(nama);
                }
                adapter = new AdapterAdmin1(daftarKonfirmasi, Admin.this);
                rv1.setAdapter(adapter);
                loading.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.dismiss();
            }
        });
    }
    private void getOngoing(){
        loading = ProgressDialog.show(Admin.this,
                null,
                "Harap tunggu...",
                true,
                false);
        db.child("List/Pesanan Masuk/Hewan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBerjalan = new ArrayList<>();
                for (DataSnapshot note : snapshot.getChildren()){
                    namas barang = note.getValue(namas.class);
                    barang.setUser(note.getKey());
                    daftarBerjalan.add(barang);
                }
                adapter = new AdapterAdmin1(daftarBerjalan, Admin.this);
                rv2.setAdapter(adapter);
                loading.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.dismiss();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin_home_menu:
                if (viewPager.getCurrentItem() == 2){
                    viewPager.setCurrentItem(1);
                }

                break;
            case R.id.admin_manage_menu:
                if (viewPager.getCurrentItem() != 2) {
                    viewPager.setCurrentItem(2);
                }
        }
        //kalo return false, warna menu ga berubah
        return true;
    }

    @Override
    public void onDataClick(namas barang, int position) {
        if (db != null){
            Toast.makeText(Admin.this, "Anda mengklik "+barang, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.admin_btn_menunggu:
                if (viewPager.getCurrentItem() == 1){
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.admin_btn_onGoing:
                if (viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(1);
                }
                break;
        }
    }

    public class viewAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public viewAdapter(){}

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(layouts[position], container, false);
            container.addView(v);

            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            View v = (View) object;
            container.removeView(v);
        }
    }
}
