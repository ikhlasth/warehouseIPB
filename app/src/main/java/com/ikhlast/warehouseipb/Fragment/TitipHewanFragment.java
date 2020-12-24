package com.ikhlast.warehouseipb.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class TitipHewanFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database, ph;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick, nohp;
    private Button titip;
    private EditText etJenis, etPenyakit, etMakanan, etVaksin, etNote;
    private ProgressDialog loading;

    public TitipHewanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.titip_hewan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alert = new AlertDialog.Builder(getContext());
        database = FirebaseDatabase.getInstance().getReference();
        ph = database.child("List/Pesanan Masuk/Hewan");
        mAuth = FirebaseAuth.getInstance();
        sessions = new Sessions(getContext());
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com","");

        //cast
        etJenis = view.findViewById(R.id.titipHewan_et_jenis_hewan);
        etPenyakit = view.findViewById(R.id.titipHewan_et_penyakit);
        etMakanan = view.findViewById(R.id.titipHewan_et_jenisMerek);
        etVaksin = view.findViewById(R.id.titipHewan_et_vaksin);
        etNote = view.findViewById(R.id.titipHewan_add_note);
        titip = view.findViewById(R.id.titipHewan_titipnow);

        titip.setOnClickListener(this);
        getNoHP(user.getUid());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titipHewan_titipnow:
                String s1 = etJenis.getText().toString();
                String s2 = etPenyakit.getText().toString();
                String s3 = etMakanan.getText().toString();
                String s4 = etVaksin.getText().toString();
                String s5 = etNote.getText().toString();
                if (s5.equals("")){
                    s5 = "-";
                }
                if (s1.equals("") && s2.equals("") && s3.equals("") && s4.equals("")) {
                    alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Eits").setMessage("Anda belum menambahkan apapun").setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create().show();
                } else if (s1.equals("")){
                    etJenis.setError("Kolom harus diisi");
                } else if (s2.equals("")){
                    etPenyakit.setError("Kolom harus diisi");
                } else if (s3.equals("")){
                    etMakanan.setError("Kolom harus diisi");
                } else if (s4.equals("")){
                    etVaksin.setError("Isikan dengan - jika tidak ada");
                } else {
                    alert = new AlertDialog.Builder(getContext());
                    alert
                            .setTitle("Titipan anda")
                            .setMessage("Anda menitipkan hewan " + s1 + " dengan penyakit " + s2 + ". Makanan yang biasa diberikan adalah " + s3 + ", dan pernah diberi vaksin " + s4 + ". Dengan catatan " + s5 + ".")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).create().show();
                    submitform(user.getUid(), nohp, s1, s2, s3, s4, s5);
                    break;
                }
        }
    }
    private void getNoHP(String uid){
        database.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               nohp =  snapshot.child("Nomor HP").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void submitform(String uid, String nomorhp, String hewan, String penyakit, String makanan, String vaksin, String catatan){
        ph.child("id").setValue(uid);
        ph.child("id").child(uid).child("nomor hp").setValue(nomorhp);
        ph.child("id").child(uid).child("hewan").setValue(hewan);
        ph.child("id").child(uid).child("penyakit hewan").setValue(penyakit);
        ph.child("id").child(uid).child("makanan hewan").setValue(makanan);
        ph.child("id").child(uid).child("vaksin hewan").setValue(vaksin);
        ph.child("id").child(uid).child("catatan khusus").setValue(catatan);
    }
}
