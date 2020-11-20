package com.ikhlast.warehouseipb.launch.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ikhlast.warehouseipb.Home;
import com.ikhlast.warehouseipb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarFragment extends BottomSheetDialogFragment {
    private AppBarLayout apl;
    private LinearLayout ll;
    EditText namaDF, passDF, konfDF;
    Button dft;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_daftar, null);
        dialog.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View)view.getParent());
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        apl = view.findViewById(R.id.appbarl1);
        ll = view.findViewById(R.id.lldaftar);
        hideV(apl);

        namaDF = view.findViewById(R.id.username_daftarF);
        passDF = view.findViewById(R.id.password_daftarF);
        konfDF = view.findViewById(R.id.password2_daftarF);
        dft = view.findViewById(R.id.daftar_frg);
        dft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namaDF.equals("") | passDF.equals("") | konfDF.equals("")){
                    namaDF.setError("Pastikan tidak ada kolom yang kosong");
                }
                else if (!(passDF.equals(konfDF))){
                    konfDF.setError("Konfirmasi password salah");
                } else {
                    startActivity(new Intent(getActivity(), Home.class));
                    setEnterTransition(0);
                    setExitTransition(0);
                }
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED){

                    dismiss();
                }
                else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    dismiss();
//                    showV(ll, getActionBarSize());
                }
                else if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    dismiss();
                }
                else if (newState == BottomSheetBehavior.STATE_DRAGGING){

                }
                else if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED){
                    dismiss();
                }
                else if (newState == BottomSheetBehavior.STATE_SETTLING){
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

//        //buat close dialog?
//        view.findViewById(R.id.daftar_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
        view.findViewById(R.id.daftar_frg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return dialog;
    }

    private void hideV(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }
    private void showV(View view, int size){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }
    private int getActionBarSize(){
        final TypedArray styled = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        return (int) styled.getDimension(0,0);
    }

    public DaftarFragment() {
        // Required empty public constructor
    }
}
