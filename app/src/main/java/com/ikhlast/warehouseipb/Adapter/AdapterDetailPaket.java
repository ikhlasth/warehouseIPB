package com.ikhlast.warehouseipb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.warehouseipb.Main.Details;
import com.ikhlast.warehouseipb.Main.DetailsBerjalan;
import com.ikhlast.warehouseipb.Models.Barang;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class AdapterDetailPaket extends RecyclerView.Adapter<AdapterDetailPaket.ViewHolder> {
    private ArrayList<ModelPaket> daftarPaket;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterDetailPaket(ArrayList<ModelPaket> barangs, Context ctx) {
        daftarPaket = barangs;
        context = ctx;
        listener = (Details)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul, desc;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.isilistdetail_namapaket);
            desc = v.findViewById(R.id.isilistdetail_deskripsipaket);
            cv = v.findViewById(R.id.isilist_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistdetailpromo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String n = daftarPaket.get(position).getJudul();
        final String j = daftarPaket.get(position).getDesc();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPaketClick(daftarPaket.get(position), position);
            }
        });

        holder.judul.setText(n);
        holder.desc.setText(j);
    }

    @Override
    public int getItemCount() {
        return daftarPaket.size();
    }

    public interface DataListener{
        void onPaketClick(ModelPaket barang, int position);
    }
}
