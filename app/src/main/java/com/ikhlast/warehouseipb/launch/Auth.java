package com.ikhlast.warehouseipb.launch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ikhlast.warehouseipb.Daftar;
import com.ikhlast.warehouseipb.R;
import com.ikhlast.warehouseipb.launch.fragment.DaftarFragment;
import com.ikhlast.warehouseipb.launch.fragment.MasukFragment;

public class Auth extends AppCompatActivity implements View.OnClickListener{
    Button dft, msk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        dft = findViewById(R.id.dft);
        msk = findViewById(R.id.msk);
        dft.setOnClickListener(this);
        msk.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dft:
                DaftarFragment df = new DaftarFragment();
                df.show(getSupportFragmentManager(), df.getTag());
                break;
            case R.id.msk:
                MasukFragment mf = new MasukFragment();
                mf.show(getSupportFragmentManager(), mf.getTag());
                break;
        }
    }
}
