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
import com.ikhlast.warehouseipb.Main.Details;
import com.ikhlast.warehouseipb.Main.Promo;
import com.ikhlast.warehouseipb.Models.Barang;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class AdapterDetailBarang extends RecyclerView.Adapter<AdapterDetailBarang.ViewHolder> {
    private ArrayList<Barang> daftarBarang;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterDetailBarang(ArrayList<Barang> barangs, Context ctx) {
        daftarBarang = barangs;
        context = ctx;
        listener = (Details)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama, jumlah;
        CardView cv;

        ViewHolder(View v){
            super(v);
            nama = v.findViewById(R.id.isilist_namabarang);
            jumlah = v.findViewById(R.id.isilist_jumlahbarang);
            cv = v.findViewById(R.id.isilist_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistdetailbarang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String n = daftarBarang.get(position).getNama();
        final String j = daftarBarang.get(position).getJumlah();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBarangClick(daftarBarang.get(position), position);
            }
        });

        holder.nama.setText("Barang: "+n);
        holder.jumlah.setText("Jumlah: "+j);
    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }

    public interface DataListener{
        void onBarangClick(Barang barang, int position);
    }
}
