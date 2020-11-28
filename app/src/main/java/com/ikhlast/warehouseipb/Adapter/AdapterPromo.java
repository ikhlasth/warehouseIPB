package com.ikhlast.warehouseipb.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterPromo extends RecyclerView.Adapter {
    private ArrayList<ModelPaket> data;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private String user;

    public AdapterPromo(ArrayList<ModelPaket> data, Context context) {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistpromo, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView gbr;
        TextView judul, deskripsi, harga;
        CardView cv;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            gbr = itemView.findViewById(R.id.isilist_gambar);
            judul = itemView.findViewById(R.id.isilist_namapaket);
            deskripsi = itemView.findViewById(R.id.isilist_deskripsipaket);
            harga = itemView.findViewById(R.id.isilist_dp);
            cv = itemView.findViewById(R.id.isilist_cardview);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.isilist_cardview:
                    break;
            }
        }
    }
}
