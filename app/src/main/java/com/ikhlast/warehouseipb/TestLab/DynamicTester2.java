package com.ikhlast.warehouseipb.TestLab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ikhlast.warehouseipb.R;

public class DynamicTester2 extends AppCompatActivity {

//    AutoCompleteTextView textIn;
    Button buttonAdd, buttonTitip;
    LinearLayout container;
    TextView reList, info;

    //
    EditText etJenis, etJumlah;

//    private static final String[] NUMBER = new String[] {
//            "One", "Two", "Three", "Four", "Five",
//            "Six", "Seven", "Eight", "Nine", "Ten"
//    };
//    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_tester2);

//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, NUMBER);
//
//        textIn = (AutoCompleteTextView)findViewById(R.id.textin);
//        textIn.setAdapter(adapter);

        etJenis = findViewById(R.id.titipBarang_entry_jenis);
        etJumlah = findViewById(R.id.titipBarang_entry_jumlah);
        buttonAdd = findViewById(R.id.add);
        buttonTitip = findViewById(R.id.titipnow);
        container = findViewById(R.id.container);
//        reList = findViewById(R.id.relist);
//        reList.setMovementMethod(new ScrollingMovementMethod());
//        info = findViewById(R.id.info);
//        info.setMovementMethod(new ScrollingMovementMethod());

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.entrybarang, null);
//                AutoCompleteTextView textOut = (AutoCompleteTextView)addView.findViewById(R.id.textout);
//                textOut.setAdapter(adapter);
//                textOut.setText(textIn.getText().toString());
                Button buttonRemove = addView.findViewById(R.id.remove);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
//                        info.append("thisListener called:\t" + this + "\n");
//                        info.append("Remove addView: " + addView + "\n\n");
                        ((LinearLayout)addView.getParent()).removeView(addView);

                        listAllAddView();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container.addView(addView);

//                info.append(
//                        "thisListener:\t" + thisListener + "\n"
//                                + "addView:\t" + addView + "\n\n"
//                );

                listAllAddView();
            }
        });

        buttonTitip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (container.getChildCount() > 0) {
                    listAllAddView();
                } else {
                    Toast.makeText(getApplicationContext(), "Anda belum menambahkan barang apapun", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void listAllAddView(){
//        reList.setText("");

        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
//            reList.append(thisChild + "\n");
//            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
//            String childTextViewValue = childTextView.getText().toString();
//            reList.append("= " + childTextViewValue + "\n");
            EditText et1 = thisChild.findViewById(R.id.titipBarang_entry_jenis);
            EditText et2 = thisChild.findViewById(R.id.titipBarang_entry_jumlah);
            String tv1 = et1.getText().toString();
            String tv2 = et2.getText().toString();
            Log.w("1", tv1+tv2);
            Toast.makeText(this, tv1+tv2, Toast.LENGTH_LONG).show();
        }
    }

}
