package com.ikhlast.warehouseipb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.warehouseipb.Main.Admin;
import com.ikhlast.warehouseipb.Models.ModelPaket;
import com.ikhlast.warehouseipb.Models.namas;
import com.ikhlast.warehouseipb.R;

import java.util.ArrayList;

public class AdapterAdmin1 extends RecyclerView.Adapter<AdapterAdmin1.ViewHolder> {
    private ArrayList<namas> namaa;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterAdmin1(ArrayList<namas> nama, Context ctx) {
        namaa = nama;
        context = ctx;
        listener = (Admin)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView fid;
        CardView cv;

        ViewHolder(View v){
            super(v);
            fid = v.findViewById(R.id.isilist_user);
            cv = v.findViewById(R.id.isilistadmin_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@whipb.com", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isilistadmin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String f = namaa.get(position).getUser();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDataClick(namaa.get(position), position);
            }
        });

        holder.fid.setText("FID: "+f);
    }

    @Override
    public int getItemCount() {
        return namaa.size();
    }

    public interface DataListener{
        void onDataClick(namas barang, int position);
    }
}
