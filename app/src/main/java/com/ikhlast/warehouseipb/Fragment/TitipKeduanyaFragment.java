package com.ikhlast.warehouseipb.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.warehouseipb.Main.Biodata;
import com.ikhlast.warehouseipb.Main.Titip;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class TitipKeduanyaFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick;
    private Button titip, tambah, hapus;
    EditText etJenisHewan, etPenyakit, etJenisMakanan, etVaksin, etNote, etJenisBarang, etJumlahBarang;
    LinearLayout container;
    ArrayList<String> isi;
    private ProgressDialog loading;

    public TitipKeduanyaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.titip_keduanya, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alert = new AlertDialog.Builder(getContext());
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sessions = new Sessions(getContext());
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com", "");

        //cast
        etJenisHewan = view.findViewById(R.id.titipKeduanya_et_jenis_hewan);
        etPenyakit = view.findViewById(R.id.titipKeduanya_et_penyakit);
        etJenisMakanan = view.findViewById(R.id.titipKeduanya_et_jenisMerek);
        etVaksin = view.findViewById(R.id.titipKeduanya_et_vaksin);
        etNote = view.findViewById(R.id.titip_keduanya_add_note);
        etJenisBarang = view.findViewById(R.id.titipBarang_entry_jenis);
        etJumlahBarang = view.findViewById(R.id.titipBarang_entry_jumlah);
        tambah = view.findViewById(R.id.titipKeduanya_tambah);
        titip = view.findViewById(R.id.titipKeduanya_titipnow);
        container = view.findViewById(R.id.titipKeduanya_container);

        //listener
        tambah.setOnClickListener(this);
        titip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titipKeduanya_tambah:
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addv = li.inflate(R.layout.entrybarang, null);
                hapus = addv.findViewById(R.id.titipBarang_entry_hapus);
                final View.OnClickListener listenerHapus = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert = new AlertDialog.Builder(getContext());
                        etJenisBarang = addv.findViewById(R.id.titipBarang_entry_jenis);
                        alert.setTitle("Hapus barang").setMessage("Yakin ingin menghapus"+ etJenisBarang.getText().toString()+"?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((LinearLayout)addv.getParent()).removeView(addv);
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setCancelable(false).create().show();
                    }
                };
                hapus.setOnClickListener(listenerHapus);
                container.addView(addv);
                break;
            case R.id.titipKeduanya_titipnow:
                if (container.getChildCount() > 0){
                    list();
                } else {
                    alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Eits").setMessage("Anda belum menambahkan apapun").setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create().show();
                }
                break;
        }
    }
    private void list(){
        isi = new ArrayList<>();
        String txt = "";
        int count = container.getChildCount();
        for (int i = 0; i<count; i++){
            View child = container.getChildAt(i);
            EditText et1 = child.findViewById(R.id.titipBarang_entry_jenis);
            EditText et2 = child.findViewById(R.id.titipBarang_entry_jumlah);
            String t1 = et1.getText().toString();
            String t2 = et2.getText().toString();
            if (t1.equals("")){
                et1.setError("Tidak boleh kosong");
                break;
            } else if (t2.equals("")){
                et2.setError("Tidak boleh kosong");
                break;
            }
            isi.add(t1+" "+t2);
            txt += t1+t2+"%%";
        }
        //TODO: ALERT MASIH ISI BARANG DOANG a.k.a belom nyambung ke database
        alert = new AlertDialog.Builder(getContext());
        alert
                .setTitle("Titipan")
                .setMessage("Barang yang ingin anda titip adalah "+isi)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
    }
}
