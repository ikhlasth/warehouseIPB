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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.warehouseipb.Main.Promo;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterPromo extends RecyclerView.Adapter<AdapterPromo.ViewHolder> {
    private ArrayList<ModelPaket> daftarPromo;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterPromo(ArrayList<ModelPaket> barang, Context ctx) {
        daftarPromo = barang;
        context = ctx;
        listener = (Promo)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul, deskripsi, harga;
        ImageView url;
        CardView cv;

        ViewHolder(View v){
            super(v);
            url = v.findViewById(R.id.isilist_gbr);
            judul = v.findViewById(R.id.isilist_namapaket);
            deskripsi = v.findViewById(R.id.isilist_deskripsipaket);
            harga = v.findViewById(R.id.isilist_dp);
            cv = v.findViewById(R.id.isilist_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistpromo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String gbr = daftarPromo.get(position).getUrl();
        final String judul = daftarPromo.get(position).getJudul();
        final String desc = daftarPromo.get(position).getDesc();
        final int dp = daftarPromo.get(position).getDp();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPromoClick(daftarPromo.get(position), position);
            }
        });

        Glide.with(context).load(gbr).into(holder.url);
        holder.judul.setText(judul);
        holder.deskripsi.setText(desc);
        holder.harga.setText("Dp : Rp."+dp);
    }

    @Override
    public int getItemCount() {
        return daftarPromo.size();
    }

    public interface DataListener{
        void onPromoClick(ModelPaket barang, int position);
    }
}
