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
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditPetsActivity extends AppCompatActivity {
    ImageView ivBack,ivImage;
    EditText tvType;
    EditText tvName, tvAge, TvBreed, interest;
    Button btnSave;

    String detail;
    List<ProfilePets> profilePetsList;
    Uri uri;

    FirebaseStorage storage;
    DatabaseHelper db;
    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            ivImage.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pets);


        ivBack = findViewById(R.id.ivBackArrow);
        ivImage = findViewById(R.id.ivImage);
        tvType = findViewById(R.id.edtType);
        tvName = findViewById(R.id.edtName);
        tvAge = findViewById(R.id.edtAge);
        TvBreed = findViewById(R.id.edtBreed);
        interest = findViewById(R.id.edtInterest);
        btnSave = findViewById(R.id.btnSave);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);


        db=new DatabaseHelper();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
         profilePetsList = new ArrayList<>();

        ProfilePets pet = (ProfilePets) getIntent().getSerializableExtra("pets");
        String petKey = pet.getKey();
        Picasso.get().load(pet.getImage()).into(ivImage);
        tvName.setText(pet.getName());
        tvType.setText(pet.getType());
        tvAge.setText(pet.getAge());
        TvBreed.setText(pet.getBreed());
        interest.setText(pet.getInterest());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

        btnSave.setOnClickListener(view -> {
            String n = tvName.getText().toString();
            String typ = tvType.getText().toString();
            String ag = tvAge.getText().toString();
            String bred = TvBreed.getText().toString();
            String inter = interest.getText().toString();

            if (n.isEmpty() || typ.isEmpty() || ag.isEmpty() || bred.isEmpty() || inter.isEmpty()) {
                Toast.makeText(this, "Please Enter All Fields!", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                // Create ProfilePets object with updated information
                ProfilePets updatedPet = new ProfilePets();
                updatedPet.setName(n);
                updatedPet.setType(typ);
                updatedPet.setAge(ag);
                updatedPet.setBreed(bred);
                updatedPet.setInterest(inter);

                if (uri != null) {
                    // If a new image is selected, upload the image and update the pet profile
                    StorageReference storageReference = storage.getReference().child("images").child(uri.getLastPathSegment());
                    storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL for the uploaded image
                        storageReference.getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String imageUrl = task.getResult().toString();
                                // Set the updated image URL to the ProfilePets object
                                updatedPet.setImage(imageUrl);
                                // Update the pet profile in the database
                                db.updateMyPetProfile(updatedPet, auth.getCurrentUser().getUid(), petKey)
                                        .addOnCompleteListener(updateTask -> {
                                            progressDialog.dismiss();
                                            if (updateTask.isSuccessful()) {
                                                updateCommunityPetsList(updatedPet);
                                                Toast.makeText(EditPetsActivity.this, "Pet updated successfully", Toast.LENGTH_SHORT).show();
                                                finish(); // Finish the activity after updating
                                            } else {
                                                Toast.makeText(EditPetsActivity.this, "Failed to update pet", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(EditPetsActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(EditPetsActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                } else {
                    // If no new image is selected, update only the pet information
                    db.updateMyPetProfile(updatedPet, auth.getCurrentUser().getUid(), petKey)
                            .addOnCompleteListener(updateTask -> {
                                progressDialog.dismiss();
                                if (updateTask.isSuccessful()) {
                                    updateCommunityPetsList(updatedPet);
                                    Toast.makeText(EditPetsActivity.this, "Pet updated successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Finish the activity after updating
                                } else {
                                    Toast.makeText(EditPetsActivity.this, "Failed to update pet", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });






    }

    private void updateCommunityPetsList(ProfilePets updatedPet) {
        // Fetch the updated list of pets
        db.getMyPets(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProfilePets> updatedPetsList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ProfilePets pets = snapshot1.getValue(ProfilePets.class);
                    updatedPetsList.add(pets);
                }
                // Update the pets list in the community model
                db.updateCommunityPetsList(auth.getCurrentUser().getUid(), updatedPetsList)
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Toast.makeText(EditPetsActivity.this, "Failed to update community pets list", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }


}