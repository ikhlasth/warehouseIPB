package com.ikhlast.warehouseipb.Credentials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikhlast.warehouseipb.Main.Home;
import com.ikhlast.warehouseipb.R;

public class Daftar extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FirebaseUser mUser;
    EditText username, password, password2;
    Button masuk, daftar;
    private String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        masuk = findViewById(R.id.masuk);
        daftar = findViewById(R.id.daftar);

        daftar.setOnClickListener(this);
        masuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.daftar:
                reg();
                break;
            case R.id.masuk:
                startActivity(new Intent(Daftar.this, Login.class));
                overridePendingTransition(0,0);
                finish();
                break;
        }
    }
    private void reg(){
        String usernameText = username.getText().toString();
        String userpassword = password.getText().toString();
        String passconfirm = password2.getText().toString();

        username.setError(null);
        password.setError(null);
        password2.setError(null);

        if (!userpassword.equals(passconfirm)) {
            password2.setError("Konfirmasi password harus sama");
            password2.requestFocus();
        } else if (usernameText.equals("")) {
            username.setError("Username tidak boleh kosong");
            username.requestFocus();
        } else if (userpassword.equals("") || userpassword.length() < 6) {
            password.setError("Password minimal 6 huruf atau angka");
            password.requestFocus();
        } else {
            usernameText = usernameText+"@whipb.com";
            mAuth.createUserWithEmailAndPassword(usernameText, userpassword)
                    .addOnCompleteListener(Daftar.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUser = mAuth.getCurrentUser();
                                usr = mAuth.getCurrentUser().getEmail().replace("@whipb.com", "");
                                database.child("user").child(mUser.getUid()).child("user").setValue(usr);
                                Toast.makeText(Daftar.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(Daftar.this, Home.class));
                                overridePendingTransition(0, 0);
                                username.setText("");
                                password.setText("");
                                password2.setText("");

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Daftar.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }
    }
}
