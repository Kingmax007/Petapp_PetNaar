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
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ActivityCreateProfile extends AppCompatActivity {
    ImageView ivBack, ivProfile;
    EditText edtName, edtEmail, edtPass, edtConfirmPass;
    Button btnSave;

    FirebaseAuth auth;

    DatabaseHelper db;

    Uri uri;

    FirebaseStorage storage;

    ProgressDialog progressDialog;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            ivProfile.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        ivProfile = findViewById(R.id.ivProfile);
        ivBack = findViewById(R.id.ivBackArrow);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtConfirmPass = findViewById(R.id.edtConfPass);
        btnSave = findViewById(R.id.btnSave);

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        db=new DatabaseHelper();


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);

       ivBack.setOnClickListener(view -> {
           Intent intent=new Intent(this,ActivityHome.class);
           startActivity(intent);
           finish();
       });


        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

        btnSave.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String password = edtPass.getText().toString();
            String confirmPass = edtConfirmPass.getText().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || uri == null) {
                Toast.makeText(this, "Please Enter all details!", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirmPass)) {
                Toast.makeText(ActivityCreateProfile.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.show();
                registerUser(name,email,password);
            }


        });

    }

    private void registerUser(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        StorageReference storageReference=storage.getReference().child("images").child(uri.getLastPathSegment());
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String userId = auth.getCurrentUser().getUid();
                                        Users user = new Users(userId, task.getResult().toString(), name);
                                        db.addUsers(user,userId).addOnCompleteListener(registerTask -> {
                                            if (registerTask.isSuccessful()) {
                                                progressDialog.hide();
                                                Toast.makeText(ActivityCreateProfile.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ActivityCreateProfile.this, ActivityLogin.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                progressDialog.hide();
                                                Toast.makeText(ActivityCreateProfile.this, "Failed to add user data to database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.hide();
                                Toast.makeText(ActivityCreateProfile.this, "Some thing Wrong"+e, Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                    else {
                        Toast.makeText(this, "Password Not Matched!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }

}