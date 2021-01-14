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
    private DatabaseReference database, pbh, dbb;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick, nohp;
    int count;
    long cb, ch, sb, sh;
    private Button titip, tambah, hapus;
    EditText etJenisHewan, etPenyakit, etJenisMakanan, etVaksin, etNote, etJenisBarang, etJumlahBarang;
    LinearLayout container;
    ArrayList<String> isi, secret;
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
        pbh = FirebaseDatabase.getInstance().getReference("List/Pesanan Masuk/Keduanya");
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

//        getnoHP(user.getUid());
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
                list();
                break;
        }
    }

    private void list(){
        isi = new ArrayList<>();
        secret = new ArrayList<>();
        final String s1 = etJenisHewan.getText().toString().trim();
        final String s2 = etPenyakit.getText().toString().trim();
        final String s3 = etJenisMakanan.getText().toString().trim();
        final String s4 = etVaksin.getText().toString().trim();
        String s5 = etNote.getText().toString().trim();
        if (s5.equals("")){
            s5 = "-";
        }
        if (s1.equals("") && s2.equals("") && s3.equals("") && s4.equals("")) {
            alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Eits").setMessage("Anda belum menambahkan hewan").setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        } else if (s1.equals("")){
            etJenisHewan.setError("Kolom harus diisi");
        } else if (s2.equals("")){
            etPenyakit.setError("Kolom harus diisi");
        } else if (s3.equals("")){
            etJenisMakanan.setError("Kolom harus diisi");
        } else if (s4.equals("")){
            etVaksin.setError("Isikan dengan - jika tidak ada");
        } else {
            count = container.getChildCount();
            if (count == 0) {
                alert = new AlertDialog.Builder(getContext());
                alert
                        .setTitle("Eits")
                        .setMessage("Anda belum mengisi barang")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setCancelable(true)
                        .create().show();
            } else {
                for (int i = 0; i < count; i++) {
                    View child = container.getChildAt(i);
                    EditText et1 = child.findViewById(R.id.titipBarang_entry_jenis);
                    EditText et2 = child.findViewById(R.id.titipBarang_entry_jumlah);
                    String t1 = et1.getText().toString().trim();
                    String t2 = et2.getText().toString().trim();
                    if (t1.equals("")) {
                        et1.setError("Tidak boleh kosong");
                        break;
                    } else if (t2.equals("")) {
                        et2.setError("Tidak boleh kosong");
                        break;
                    } else {
                        secret.add("true");
                    }
                    isi.add(t1 + " " + t2);
                }
            }
        }

        if (isi.size()>0 && secret.size() == count){
            alert = new AlertDialog.Builder(getContext());
            final String finalS = s5;
            alert
                    .setTitle("Titipan")
                    .setMessage("Barang yang ingin anda titip adalah "
                            + String.valueOf(isi).replace("[", "")
                            .replace("]", "")
                            +" Dan hewan "+s1+" dengan penyakit "
                            + s2 + ". Makanan yang biasa diberikan adalah "
                            + s3 + ", dan pernah diberi vaksin " + s4
                            + ". Dengan catatan " + s5 + ". "+"Serta barang "+(String.valueOf(isi).replace("[", "").replace("]", "")))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            proceed(user.getUid(), s1,s2,s3,s4, finalS);
                        }
                    }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        }

    }
//    public void getnoHP(String uid){
//        database.child("user").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nohp = snapshot.child("Nomor HP").getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private void proceed(final String uid, final String hewan, final String penyakit, final String makanan, final String vaksin, final String catatan){
        pbh.child("id/"+user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    pbh.child("id/"+uid+"/Barang").setValue(uid);
                } else if (!snapshot.child("Barang").exists()){
                    pbh.child("id/"+uid+"/Barang").setValue("null");
                } else if (!snapshot.child("Hewan").exists()){
                    pbh.child("id/"+uid+"/Hewan").setValue("null");
                }
                //barang
                for (int b = 0; b < isi.size(); b++){
                    String[] x = isi.get(b).split(" ");
                    cb = snapshot.child("Barang").getChildrenCount();
                    sb = cb+b+1;
                    if (sb<10){
                        pbh.child("id/"+uid+"/Barang/Barang 0"+sb+"/nama").setValue(x[0]);
                        pbh.child("id/"+uid+"/Barang/Barang 0"+sb+"/jumlah").setValue(x[1]);
                    } else {
                        pbh.child("id/"+uid+"/Barang/Barang "+sb+"/nama").setValue(x[0]);
                        pbh.child("id/"+uid+"/Barang/Barang "+sb+"/jumlah").setValue(x[1]);
                    }
                }

                //hewan
                ch = snapshot.child("Hewan").getChildrenCount();
                sh = ch+1;
                if (sh < 10){
                    dbb = FirebaseDatabase.getInstance().getReference("List/Pesanan Masuk/Keduanya/id/"+uid+"/Hewan/Hewan 0"+sh);
                } else {
                    dbb = FirebaseDatabase.getInstance().getReference("List/Pesanan Masuk/Keduanya/id/"+uid+"/Hewan/Hewan "+sh);
                }
                dbb.child("jenis").setValue(hewan);
                dbb.child("penyakit").setValue(penyakit);
                dbb.child("makanan").setValue(makanan);
                dbb.child("vaksin").setValue(vaksin);
                dbb.child("catatan").setValue(catatan);
                alert = new AlertDialog.Builder(getContext());
                alert
                        .setTitle("Sukses")
                        .setMessage("Data berhasil dikirim. Pihak warehouseIPB akan menghubungi anda untuk proses selanjutnya")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
