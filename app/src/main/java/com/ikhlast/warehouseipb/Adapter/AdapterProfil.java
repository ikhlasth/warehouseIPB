package com.ikhlast.warehouseipb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.warehouseipb.Main.Profil;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class AdapterProfil extends RecyclerView.Adapter<AdapterProfil.ViewHolder> {
    private ArrayList<ModelPaket> daftarProfil;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterProfil(ArrayList<ModelPaket> barang, Context ctx) {
        daftarProfil = barang;
        context = ctx;
        listener = (Profil)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul, deskripsi, harga;
        ImageView url;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.riwayat_namapaket);
            deskripsi = v.findViewById(R.id.riwayat_deskripsipaket);
            harga = v.findViewById(R.id.riwayat_dp);
            cv = v.findViewById(R.id.riwayat_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistriwayat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String gbr = daftarProfil.get(position).getUrl();
        final String judul = daftarProfil.get(position).getJudul();
        final String desc = daftarProfil.get(position).getDesc();
        final int dp = daftarProfil.get(position).getDp();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRiwayatClick(daftarProfil.get(position), position);
            }
        });

        holder.judul.setText(judul);
        holder.deskripsi.setText(desc);
        holder.harga.setText("Rp."+dp);
    }

    @Override
    public int getItemCount() {
        return daftarProfil.size();
    }

    public interface DataListener{
        void onRiwayatClick(ModelPaket barang, int position);
    }
}
