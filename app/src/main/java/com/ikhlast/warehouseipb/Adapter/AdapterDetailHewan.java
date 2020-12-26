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
import com.ikhlast.warehouseipb.Models.Hewan;
import com.ikhlast.warehouseipb.Models.Hewan;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class AdapterDetailHewan extends RecyclerView.Adapter<AdapterDetailHewan.ViewHolder> {
    private ArrayList<Hewan> daftarHewan;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterDetailHewan(ArrayList<Hewan> hewans, Context ctx) {
        daftarHewan = hewans;
        context = ctx;
        listener = (Details)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView jenis, penyakit, makanan, vaksin, catatan;
        CardView cv;

        ViewHolder(View v){
            super(v);
            jenis = v.findViewById(R.id.isilist_jenishewan);
            penyakit = v.findViewById(R.id.isilist_penyakithewan);
            makanan = v.findViewById(R.id.isilist_makananhewan);
            vaksin = v.findViewById(R.id.isilist_vaksinhewan);
            catatan = v.findViewById(R.id.isilist_catatan);
            cv = v.findViewById(R.id.isilist_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistdetailhewan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String j = daftarHewan.get(position).getJenis();
        final String m = daftarHewan.get(position).getMakanan();
        final String p = daftarHewan.get(position).getPenyakit();
        final String v = daftarHewan.get(position).getVaksin();
        final String c = daftarHewan.get(position).getCatatan();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onHewanClick(daftarHewan.get(position), position);
            }
        });

        holder.jenis.setText("Jenis: "+j);
        holder.penyakit.setText("Penyakit: "+p);
        holder.makanan.setText("Makanan: "+m);
        holder.vaksin.setText("Vaksin: "+v);
        holder.catatan.setText("Catatan: "+c);
    }

    @Override
    public int getItemCount() {
        return daftarHewan.size();
    }

    public interface DataListener{
        void onHewanClick(Hewan barang, int position);
    }
}
