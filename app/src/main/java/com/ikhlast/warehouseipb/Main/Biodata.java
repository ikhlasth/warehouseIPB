package com.ikhlast.warehouseipb.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ikhlast.warehouseipb.Preferences.BiodataPref;
import com.ikhlast.warehouseipb.R;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Biodata extends AppCompatActivity implements View.OnClickListener {
    Button pilih, kirim;
    EditText nama, alamatasli, alamatkos;
    ImageView gambar;
    String nick;
    int permission_all = 1;

    ProgressBar pbar;
    AlertDialog.Builder alert;
    StorageReference ref;
    BiodataPref biopref;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference database;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biopref = new BiodataPref(this);
        if (biopref.isRegistered()){
            toTitip();
        }

        setContentView(R.layout.biodata);

        String[] Permissions = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET};
        if (!hasPermissions(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, permission_all);
        }


        nama = findViewById(R.id.biodata_namalengkap);
        alamatasli = findViewById(R.id.biodata_alamatasli);
        alamatkos = findViewById(R.id.biodata_alamatkos);
        pilih = findViewById(R.id.biodata_pilihfoto);
        kirim = findViewById(R.id.biodata_kirim);
        gambar = findViewById(R.id.biodata_fotoktp);
        gambar.setVisibility(View.GONE);
        pbar = findViewById(R.id.biodata_progressBar);

        pilih.setOnClickListener(this);
        kirim.setOnClickListener(this);

        alert = new AlertDialog.Builder(this);
        ref = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        nick = user.getEmail().replace("@whipb.com","");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK){
                    gambar.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    gambar.setImageBitmap(bitmap);
                } break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    gambar.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    gambar.setImageURI(uri);
                } break;
        }
    }

    public void pilihfoto(){
        CharSequence[] menu = {"Kamera", "Galeri"};
        alert
                .setTitle("Pilih foto dari")
                .setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent kamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(kamera, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                Intent galeri = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galeri, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                }).create().show();
    }

    public void toStorage(){
        String namaL = nama.getText().toString();
        String alamatrumah = alamatasli.getText().toString();
        String alamatkosan = alamatkos.getText().toString();

        //get image as byte
        gambar.setDrawingCacheEnabled(true);
        gambar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) gambar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //bitmap to jpg 100% qual
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        //saveLocation
//        String namaFile = UUID.randomUUID()+".jpg";
        String namafile = "KTP_"+nick+".png";
        String pathimg = "ktp/"+namafile;
        final UploadTask uploadTask = ref.child(pathimg).putBytes(bytes);
        database.child("user").child(user.getUid()).child("Nama Lengkap").setValue(namaL);
        database.child("user").child(user.getUid()).child("Alamat Rumah").setValue(alamatrumah);
        database.child("user").child(user.getUid()).child("Alamat Kosan").setValue(alamatkosan);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pbar.setVisibility(View.GONE);
                Toast.makeText(Biodata.this, "Berhasil mengirim biodata", Toast.LENGTH_LONG).show();
                toTitip();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(Biodata.this, "Gagal mengirim biodata", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        pbar.setVisibility(View.VISIBLE);
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pbar.setProgress((int) progress);
                    }
                });
    }

    public void checkEntry() {
        String namaL = nama.getText().toString();
        String alamatrumah = alamatasli.getText().toString();
        String alamatkosan = alamatkos.getText().toString();

        nama.setError(null);
        alamatasli.setError(null);
        alamatkos.setError(null);

        if (namaL.equals("")) {
            nama.setError("Nama tidak boleh kosong");
            nama.requestFocus();
        } else if (alamatrumah.equals("")) {
            alamatasli.setError("Alamat rumah tidak boleh kosong");
            alamatasli.requestFocus();
        } else if (alamatkosan.equals("")) {
            alamatkos.setError("Alamat kos/kontrakan tidak boleh kosong");
            alamatkos.requestFocus();
        } else if (gambar.getDrawable() == null){
            alert.setTitle("Foto KTP masih kosong")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            toStorage();
        }
    }

    public void toTitip(){
        biopref.setFirstTimeRegister(true);
        startActivity(new Intent(Biodata.this, Titip.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.biodata_pilihfoto:
                pilihfoto();
                break;
            case R.id.biodata_kirim:
                checkEntry();
                break;
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
