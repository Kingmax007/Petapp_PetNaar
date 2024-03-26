package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ActivityAddClinic extends AppCompatActivity {
    ImageView ivBack, addImage;
   EditText edtName, edtAddress, edtNumber;
   Button btnSave;

    DatabaseHelper db;

    Uri uri;

    FirebaseStorage storage;

    ProgressDialog progressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            addImage.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        ivBack = findViewById(R.id.ivBackArrow);
        addImage = findViewById(R.id.ivCamera);
        edtName= findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtNumber= findViewById(R.id.edtNumber);
        btnSave = findViewById(R.id.btnSave);


        db=new DatabaseHelper();
        storage=FirebaseStorage.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");
        progressDialog.setCancelable(false);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        addImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

        btnSave.setOnClickListener(view -> {
            String name=edtName.getText().toString();
            String address=edtAddress.getText().toString();
            String number =edtNumber.getText().toString();
            if (name.isEmpty() || address.isEmpty() ||number.isEmpty() || uri==null){
                Toast.makeText(this,"Please Enter All Fields!",Toast.LENGTH_SHORT).show();

            }
            else {
                progressDialog.show();
                petClinics(name,address,number);
            }

        });

    }

    private void petClinics(String name, String address,String number) {
        StorageReference storageReference=storage.getReference().child("Pet_Clinics").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        PetClinics clinics =new PetClinics(task.getResult().toString(),name,address,number);
                        db.addPetClinics(clinics).addOnCompleteListener(registerTask -> {
                            if (registerTask.isSuccessful()) {
                                progressDialog.hide();
                                Toast.makeText(ActivityAddClinic.this, "Place Added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityAddClinic.this, ActivityPetClinics.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.hide();
                                Toast.makeText(ActivityAddClinic.this, "Failed to add data to database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(ActivityAddClinic.this, "Some thing Wrong"+e, Toast.LENGTH_SHORT).show();

            }
        });
    }
}