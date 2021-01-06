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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailBarang;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailHewan;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailPaket;
import com.ikhlast.warehouseipb.Adapter.AdapterDetailPromo;
import com.ikhlast.warehouseipb.Adapter.AdapterPromo;
import com.ikhlast.warehouseipb.Models.Barang;
import com.ikhlast.warehouseipb.Models.Hewan;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailPromo extends AppCompatActivity implements View.OnClickListener,  AdapterDetailPromo.DataListener {
    private TextView tvNama, tvTanggal;
    private Button btback, btPesan, btWa;
    private String data, userID, hp;
    private ArrayList<ModelPaket> daftarPaket;
    private RecyclerView rvPaket;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmrvPaket;

    private DatabaseReference database;
    private FirebaseUser fUser;
    private FirebaseAuth fAuth;
    private ProgressDialog loading;
    private AlertDialog.Builder alert;
    DateFormat time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpromo);

        Bundle b = getIntent().getExtras();
//        tanggal = time.getDateTimeInstance().format(new Date());
//        tanggalan(tanggal);
        database = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userID = fUser.getUid();
        tvNama = findViewById(R.id.detailpromo_tvNamaPaket);
        btback = findViewById(R.id.detailpromo_btBack);
        btPesan = findViewById(R.id.detailpromo_btPesan);
        btWa = findViewById(R.id.detailpromo_btwa);
        btback.setOnClickListener(this);
        btPesan.setOnClickListener(this);
        btWa.setOnClickListener(this);
        rvPaket = findViewById(R.id.detailpromo_rvPaket);
        rvPaket.setHasFixedSize(true);
        lmrvPaket = new LinearLayoutManager(this);
        rvPaket.setLayoutManager(lmrvPaket);
        loading = ProgressDialog.show(DetailPromo.this,
                null,
                "Harap tunggu...",
                true,
                false);
        if (b != null) {
            initiateData(b);
        }
    }

    private void initiateData(Bundle bundle){
        data = bundle.getString("512");
        database.child("Promo/"+data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarPaket = new ArrayList<>();
                ModelPaket pk = snapshot.getValue(ModelPaket.class);
                pk.setJudul(snapshot.getKey());
                daftarPaket.add(pk);
                tvNama.setText("Nama paket: "+snapshot.getKey());
                adapter = new AdapterDetailPromo(daftarPaket, DetailPromo.this);
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
            case R.id.detailpromo_btBack:
                onBackPressed();
                break;
            case R.id.detailpromo_btPesan:
                alert = new AlertDialog.Builder(getWindow().getContext());
                alert
                        .setTitle("Pesan")
                        .setMessage("Pesan paket?")
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
            case R.id.detailpromo_btwa:
                if (hp != null) {

                    alert = new AlertDialog.Builder(this);
                    alert
                            .setTitle("Kontak Whatsapp Admin")
                            .setMessage("Ingin menghubungi admin via Whatsapp?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "https://api.whatsapp.com/send?phone=+6287775234085";
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
        database.child("List/Pesanan Masuk/Paket/id/"+userID+"/"+data).setValue(daftarPaket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "Berhasil dipesan", Toast.LENGTH_LONG).show();
                alert = new AlertDialog.Builder(getWindow().getContext());
                alert
                        .setTitle("Sukses")
                        .setMessage("Data berhasil dikirim. Pihak warehouseIPB akan menghubungi anda untuk proses selanjutnya")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                onBackPressed();
                            }
                        }).create().show();
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
    public void onPaketClick(ModelPaket barang, int position) {

    }
}
