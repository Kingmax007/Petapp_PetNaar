package com.example.newpetapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {

    ImageView ivProfile,ivBack;

    EditText edtName;

    Button btnSave;

    Uri uri;

    String userName,image;

    FirebaseAuth auth;

    DatabaseHelper db;

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
        setContentView(R.layout.activity_edit_profile);

        ivProfile=findViewById(R.id.ivProfile);
        edtName=findViewById(R.id.edtName);
        btnSave=findViewById(R.id.btnSave);
        ivBack=findViewById(R.id.ivBackArrow);

        userName =getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");

        Picasso.get().load(image).into(ivProfile);
        edtName.setText(userName);

        db=new DatabaseHelper();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);


        ivBack.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityProfile.class);
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
            if (name.isEmpty() || uri == null) {
                Toast.makeText(this, "Empty Fields!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                if (uri != null) {
                    // Upload the new profile image
                    StorageReference storageReference = storage.getReference().child("images").child(uri.getLastPathSegment());
                    storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL for the uploaded image
                        storageReference.getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String imageUrl = task.getResult().toString();
                                // Create a Users object with the updated name and image URL
                                Users updatedUser = new Users(auth.getCurrentUser().getUid(), imageUrl, name);
                                // Update the user profile in the database
                                db.updateUserProfile(updatedUser, auth.getCurrentUser().getUid())
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                progressDialog.hide();
                                                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(this,ActivityProfile.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                progressDialog.hide();
                                                Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                progressDialog.hide();
                                Toast.makeText(EditProfileActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).addOnFailureListener(e -> {
                        progressDialog.hide();
                        Toast.makeText(EditProfileActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    // If no new image is selected, update only the name
                    Users updatedUser = new Users(auth.getCurrentUser().getUid(), image, name);
                    db.updateUserProfile(updatedUser, auth.getCurrentUser().getUid())
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    progressDialog.hide();
                                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Finish the activity after updating the profile
                                } else {
                                    progressDialog.hide();
                                    Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}