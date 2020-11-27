package com.ikhlast.warehouseipb.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.ikhlast.warehouseipb.Main.Home;
import com.ikhlast.warehouseipb.Main.Titip;
import com.ikhlast.warehouseipb.Preferences.Sessions;
import com.ikhlast.warehouseipb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;
    AlertDialog.Builder alert;
    Sessions sessions;
    String nick;
    private Button titip;
    private ProgressDialog loading;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alert = new AlertDialog.Builder(getContext());
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sessions = new Sessions(getContext());
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com","");

        titip = view.findViewById(R.id.home_tb_titip);
        titip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(getContext(),
                        null,
                        "Mengecek biodata...",
                        true,
                        false);
                check();
            }
        });
    }
    private void check(){
        database.child("user").child(user.getUid()).child("Foto KTP").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    startActivity(new Intent(getActivity(), Titip.class));
                    loading.dismiss();
                } else {
                    startActivity(new Intent(getActivity(), Biodata.class));
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
