package com.ikhlast.warehouseipb.TestLab;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.LinearLayout.LayoutParams;
import android.os.Bundle;

import com.ikhlast.warehouseipb.R;

public class DinamicLayout extends Activity implements OnClickListener{

    private static final int MY_BUTTON = 9000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinamic_layout);

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayout2);

        // add text view
        TextView tv = new TextView(this);
        tv.setText("Dynamic Text!");
        ll.addView(tv);

        // add edit text
        EditText et = new EditText(this);
        et.setText("Dynamic EditText!");
        et.setMinLines(1);
        et.setMaxLines(3);
        ll.addView(et);

        // add button
        Button b = new Button(this);
        b.setText("Button added dynamically!");
        b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        b.setId(MY_BUTTON);
        b.setOnClickListener(this);
        ll.addView(b);

        //add checkboxes
        for(int i = 0; i < 10; i++) {
            CheckBox cb = new CheckBox(this);
            cb.setText("Dynamic Checkbox " + i);
            cb.setId(i+10);
            ll.addView(cb);
        }

        //add radio buttons
        final RadioButton[] rb = new RadioButton[5];
        RadioGroup rg = new RadioGroup(this); //create the RadioGroup
        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        for(int i=0; i<5; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText("Dynamic Radio Button " + i);
            rb[i].setId(i);
            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout

        }
        ll.addView(rg);//you add the whole RadioGroup to the layout

        // add Toggle button
        ToggleButton tb = new ToggleButton(this);
        tb.setTextOn("Dynamic Toggle Button - ON");
        tb.setTextOff("Dynamic Toggle Button - OFF");
        tb.setChecked(true);
        tb.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ll.addView(tb);

    }

    public void onClick(View v) {
        Toast toast;
        Log.w("ANDROID DYNAMIC VIEWS:", "View Id: " + v.getId());
        switch (v.getId()) {
            case MY_BUTTON:
                toast = Toast.makeText(this, "Clicked on my dynamically added button!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
                saveAnswers();
                break;
            // More buttons go here (if any) ...

        }
    }

    public void saveAnswers() {
        LinearLayout root = (LinearLayout) findViewById(R.id.linearLayout1); //or whatever your root control is
        loopQuestions(root);
    }

    private void loopQuestions(ViewGroup parent) {
        for(int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if(child instanceof RadioGroup ) {
                //Support for RadioGroups
                RadioGroup radio = (RadioGroup)child;
                storeAnswer(radio.getId(), radio.getCheckedRadioButtonId());
            }
            else if(child instanceof CheckBox) {
                //Support for Checkboxes
                CheckBox cb = (CheckBox)child;
                int answer = cb.isChecked() ? 1 : 0;
                storeAnswer(cb.getId(), answer);
            }
            else if(child instanceof EditText) {
                //Support for EditText
                EditText et = (EditText)child;
                Log.w("ANDROID DYNAMIC VIEWS:", "EdiText: " + et.getText());
            }
            else if(child instanceof ToggleButton) {
                //Support for ToggleButton
                ToggleButton tb = (ToggleButton)child;
                Log.w("ANDROID DYNAMIC VIEWS:", "Toggle: " + tb.getText());
            }
            else {
                //Support for other controls
            }

            if(child instanceof ViewGroup) {
                //Nested Q&A
                ViewGroup group = (ViewGroup)child;
                loopQuestions(group);
            }
        }
    }

    private void storeAnswer(int question, int answer) {
        Log.w("ANDROID DYNAMIC VIEWS:", "Question: " + String.valueOf(question) + " * "+ "Answer: " + String.valueOf(answer) );

        Toast toast = Toast.makeText(this, String.valueOf(question) + " * "+ "Answer: " + String.valueOf(answer), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();


    }

}
