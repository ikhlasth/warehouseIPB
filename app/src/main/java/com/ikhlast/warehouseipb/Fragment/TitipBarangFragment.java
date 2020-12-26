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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
public class TitipBarangFragment extends Fragment implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database, pb;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick, nohp;
    long count;
    private Button titip, tambahBarang, titipBarang, hapusBarang;
    EditText etJenisBarang, etJumlahBarang;
    LinearLayout containerTitip;
    private ProgressDialog loading;

    ArrayList<String> isibarang;

    public TitipBarangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.titip_barang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alert = new AlertDialog.Builder(getContext());
        database = FirebaseDatabase.getInstance().getReference();
        pb = database.child("List/Pesanan Masuk/Barang");
        mAuth = FirebaseAuth.getInstance();
        sessions = new Sessions(getContext());
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com", "");

        //barang
        etJenisBarang = view.findViewById(R.id.titipBarang_entry_jenis);
        etJumlahBarang = view.findViewById(R.id.titipBarang_entry_jumlah);
        tambahBarang = view.findViewById(R.id.titipBarang_tambah);
        titipBarang = view.findViewById(R.id.titipBarang_titipnow);
        containerTitip = view.findViewById(R.id.titipBarang_container);

        tambahBarang.setOnClickListener(this);
        titipBarang.setOnClickListener(this);

//        getNoHP(user.getUid());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titipBarang_tambah:
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.entrybarang, null);
                hapusBarang = addView.findViewById(R.id.titipBarang_entry_hapus);
                final View.OnClickListener listenerHapus = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert = new AlertDialog.Builder(getContext());
                        etJenisBarang = addView.findViewById(R.id.titipBarang_entry_jenis);
                        alert.setTitle("Hapus barang").setMessage("Yakin ingin menghapus "+ etJenisBarang.getText().toString()+"?").setPositiveButton("ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((LinearLayout)addView.getParent()).removeView(addView);
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setCancelable(false).create().show();
                    }
                };
                hapusBarang.setOnClickListener(listenerHapus);
                containerTitip.addView(addView);
//                listAll();
                break;
            case R.id.titipBarang_titipnow:
                if (containerTitip.getChildCount() > 0){
                    listAll();
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
    private void listAll(){
        isibarang = new ArrayList<>();
        final int childCount = containerTitip.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = containerTitip.getChildAt(i);
            EditText et1 = thisChild.findViewById(R.id.titipBarang_entry_jenis);
            EditText et2 = thisChild.findViewById(R.id.titipBarang_entry_jumlah);
            String tv1 = et1.getText().toString().trim();
            String tv2 = et2.getText().toString().trim();
            if (tv1.equals("")){
                et1.setError("Tidak boleh kosong");
                break;
            } else if (tv2.equals("")){
                et2.setError("Tidak boleh kosong");
                break;
            }
            isibarang.add(tv1+" "+tv2);
        }
        if (isibarang.size() > 0) {

            alert = new AlertDialog.Builder(getContext());
            alert
                    .setTitle("Konfirmasi penitipan")
                    .setMessage("Anda akan menitipkan " + (String.valueOf(isibarang).replace("[", "").replace("]", "")))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                checkProceed();
                        }
                    }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        }
    }
//    private void getNoHP(String uid){
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

    private void checkProceed(){
        pb.child("id/"+ user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    pb.child("id/"+user.getUid()+"/Barang 01").setValue(user.getUid());
                }
                for (int f = 0; f < isibarang.size(); f++) {
                    String[] x = isibarang.get(f).split(" ");
                    count = snapshot.getChildrenCount();
                    long xf = f+count+1;
                    if (xf < 10){
                        pb.child("id").child(user.getUid()).child("Barang 0" + (xf)).child("nama").setValue(x[0]);
                        pb.child("id").child(user.getUid()).child("Barang 0" + (xf)).child("jumlah").setValue(x[1]);
                    } else {
                        pb.child("id").child(user.getUid()).child("Barang " + (xf)).child("nama").setValue(x[0]);
                        pb.child("id").child(user.getUid()).child("Barang " + (xf)).child("jumlah").setValue(x[1]);
                    }
                }
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
