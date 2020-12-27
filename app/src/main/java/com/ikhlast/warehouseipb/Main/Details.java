package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailBarang;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailHewan;
import com.ikhlast.warehouseipb.Models.Barang;
import com.ikhlast.warehouseipb.Models.Hewan;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class Details extends AppCompatActivity implements AdapterDetailBarang.DataListener, AdapterDetailHewan.DataListener, View.OnClickListener {
    private TextView tvUser, tvID;
    private Button btback, btKonf, btwa;
    private String data, username, hp;
    private ArrayList<Barang> daftarBarang;
    private ArrayList<Hewan> daftarHewan;
    private RecyclerView rvHewan, rvBarang;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmrvHewan, lmrvBarang;

    private DatabaseReference database;
    private ProgressDialog loading;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Bundle b = getIntent().getExtras();
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
        rvHewan.setHasFixedSize(true);
        rvBarang.setHasFixedSize(true);
        lmrvHewan = new LinearLayoutManager(this);
        lmrvBarang = new LinearLayoutManager(this);
        rvHewan.setLayoutManager(lmrvHewan);
        rvBarang.setLayoutManager(lmrvBarang);
        loading = ProgressDialog.show(Details.this,
                null,
                "Harap tunggu...",
                true,
                false);
        getAllHewan();
        getAllBarang();
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
        database.child("List/Pesanan Masuk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarHewan = new ArrayList<>();
                for (DataSnapshot n : snapshot.child("Hewan/id/"+data).getChildren()) {
                    Hewan h = n.getValue(Hewan.class);
                    daftarHewan.add(h);
                }
                for (DataSnapshot n1 : snapshot.child("Keduanya/id/"+data+"/Hewan").getChildren()){
                    Hewan h1 = n1.getValue(Hewan.class);
                    daftarHewan.add(h1);
                }
                adapter = new AdapterDetailHewan(daftarHewan, Details.this);
                rvHewan.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllBarang(){
        database.child("List/Pesanan Masuk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBarang = new ArrayList<>();
                for (DataSnapshot n : snapshot.child("Barang/id/"+data).getChildren()) {
                    Barang b = n.getValue(Barang.class);
                    daftarBarang.add(b);
                }
                for (DataSnapshot n1 : snapshot.child("Keduanya/id/"+data+"/Barang").getChildren()){
                    Barang b1 = n1.getValue(Barang.class);
                    daftarBarang.add(b1);
                }
                adapter = new AdapterDetailBarang(daftarBarang, Details.this);
                rvBarang.setAdapter(adapter);
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
                        .setTitle("Konfirmasi")
                        .setMessage("Konfirmasi pesanan?")
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
        database.child("List/Sedang Berjalan/id/"+data+"/Barang").setValue(daftarBarang);
        database.child("List/Sedang Berjalan/id/"+data+"/Hewan").setValue(daftarHewan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Berhasil dikonfirmasi", Toast.LENGTH_LONG).show();
            }
        });
        database.child("List/Pesanan Masuk").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Barang/id/"+data).exists()){
                    database.child("List/Pesanan Masuk/Barang/id/"+data).removeValue();
                }
                if (snapshot.child("Hewan/id/"+data).exists()){
                    database.child("List/Pesanan Masuk/Hewan/id/"+data).removeValue();
                }
                if (snapshot.child("Keduanya/id/"+data).exists()){
                    database.child("List/Pesanan Masuk/Keduanya/id/"+data).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }

    public void onHewanClick(Hewan hewan, final int position){
        if (database != null){
            Toast.makeText(Details.this, "eh kepencet "+hewan, Toast.LENGTH_LONG).show();
        }
    }
    public void onBarangClick(Barang barang, final int position){
        if (database != null){
            Toast.makeText(Details.this, "eh kepencet "+barang, Toast.LENGTH_LONG).show();
        }
    }
}
