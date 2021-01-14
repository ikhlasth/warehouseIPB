package com.ikhlast.warehouseipb.Main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailBarang;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailBarangBerjalan;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailHewan;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailHewanBerjalan;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailPaket;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailPaketBerjalan;
import com.ikhlast.warehouseipb.Models.Barang;
import com.ikhlast.warehouseipb.Models.Hewan;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailsBerjalan extends AppCompatActivity implements AdapterDetailBarangBerjalan.DataListener, AdapterDetailHewanBerjalan.DataListener, View.OnClickListener, AdapterDetailPaketBerjalan.DataListener {
    private TextView tvUser, tvID;
    private Button btback, btKonf, btwa;
    private String data, username, hp, tanggal;
    private ArrayList<Barang> daftarBarang;
    private ArrayList<Hewan> daftarHewan;
    private ArrayList<ModelPaket> daftarPaket;
    private RecyclerView rvHewan, rvBarang, rvPaket;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmrvHewan, lmrvBarang, lmrvPaket;

    private DatabaseReference database;
    private ProgressDialog loading;
    private AlertDialog.Builder alert;

    DateFormat time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsberjalan);

        Bundle b = getIntent().getExtras();
        tanggal = time.getDateTimeInstance().format(new Date());
        tanggal = tanggal.replace(".", ":");
        database = FirebaseDatabase.getInstance().getReference();
        tvUser = findViewById(R.id.details_tvUser);
        tvID = findViewById(R.id.details_tvidUser);
        btback = findViewById(R.id.details_btBack);
        btwa = findViewById(R.id.details_btwa);
        btKonf = findViewById(R.id.details_btKonfirmasi);
        btback.setOnClickListener(this);
        btwa.setOnClickListener(this);
        btKonf.setOnClickListener(this);
        if (b != null) {
            initiateData(b);
        }

        rvHewan = findViewById(R.id.details_rvHewan);
        rvBarang = findViewById(R.id.details_rvBarang);
        rvPaket = findViewById(R.id.details_rvPaket);
        rvHewan.setHasFixedSize(true);
        rvBarang.setHasFixedSize(true);
        rvPaket.setHasFixedSize(true);
        lmrvHewan = new LinearLayoutManager(this);
        lmrvBarang = new LinearLayoutManager(this);
        lmrvPaket = new LinearLayoutManager(this);
        rvHewan.setLayoutManager(lmrvHewan);
        rvBarang.setLayoutManager(lmrvBarang);
        rvPaket.setLayoutManager(lmrvPaket);
        loading = ProgressDialog.show(DetailsBerjalan.this,
                null,
                "Harap tunggu...",
                true,
                false);
        getAllHewan();
        getAllBarang();
        getAllPaket();
        getHp();

    }

    private void getHp() {
        database.child("user/"+data+"/Nomor HP").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hp = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initiateData(Bundle bundle){
        data = bundle.getString("CODE");
        database.child("user/"+data+"/user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(String.class);
                tvUser.setText("User: "+username);
                tvID.setText("FID: "+data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllHewan(){
        database.child("List/Sedang Berjalan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarHewan = new ArrayList<>();
                for (DataSnapshot n : snapshot.child("id/"+data+"/Hewan").getChildren()) {
                    Hewan h = n.getValue(Hewan.class);
                    daftarHewan.add(h);
                }
                adapter = new AdapterDetailHewanBerjalan(daftarHewan, DetailsBerjalan.this);
                rvHewan.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllBarang(){
        database.child("List/Sedang Berjalan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBarang = new ArrayList<>();
                for (DataSnapshot n : snapshot.child("id/"+data+"/Barang").getChildren()) {
                    Barang b = n.getValue(Barang.class);
                    daftarBarang.add(b);
                }
                adapter = new AdapterDetailBarangBerjalan(daftarBarang, DetailsBerjalan.this);
                rvBarang.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getAllPaket(){
        database.child("List/Sedang Berjalan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarPaket = new ArrayList<>();
                for (DataSnapshot n : snapshot.child("id/"+data+"/Paket").getChildren()){

                        //??
                        ModelPaket nn = n.getValue(ModelPaket.class);
                        daftarPaket.add(nn);
                        loading.dismiss();
                }
                adapter = new AdapterDetailPaketBerjalan(daftarPaket, DetailsBerjalan.this);
                rvPaket.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_btBack:
                onBackPressed();
                break;
            case R.id.details_btKonfirmasi:
                alert = new AlertDialog.Builder(getWindow().getContext());
                alert
                        .setTitle("Selesaikan")
                        .setMessage("Selesaikan pesanan?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                konfirm();
                                dialogInterface.cancel();
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
                break;
            case R.id.details_btwa:
                if (hp != null) {

                    alert = new AlertDialog.Builder(this);
                    alert
                            .setTitle("Kontak Whatsapp Klien")
                            .setMessage("Ingin menghubungi " + hp.replaceFirst("0", "+62") + " via Whatsapp?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "https://api.whatsapp.com/send?phone=" + hp.replaceFirst("0", "+62");
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
                }
                break;

        }
    }
    private void konfirm(){
        database.child("user/"+data+"/riwayat/"+tanggal+"/"+"barang").setValue(daftarBarang);
        database.child("user/"+data+"/riwayat/"+tanggal+"/"+"paket").setValue(daftarPaket);
        database.child("user/"+data+"/riwayat/"+tanggal+"/"+"hewan").setValue(daftarHewan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Berhasil diselesaikan", Toast.LENGTH_LONG).show();
            }
        });
        database.child("List/Sedang Berjalan/id/"+data).removeValue();
//        onBackPressed();
        startActivity(new Intent(DetailsBerjalan.this, Admin.class));
        overridePendingTransition(0,0);
        finish();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }

    public void onHewanClick(Hewan hewan, final int position){
        if (database != null){
            Toast.makeText(DetailsBerjalan.this, "eh kepencet "+hewan, Toast.LENGTH_LONG).show();
        }
    }
    public void onBarangClick(Barang barang, final int position){
        if (database != null){
            Toast.makeText(DetailsBerjalan.this, "eh kepencet "+barang, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaketClick(ModelPaket barang, int position) {
        if (database != null){
            Toast.makeText(DetailsBerjalan.this, "eh kepencet "+barang, Toast.LENGTH_LONG).show();
        }
    }
}
