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
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.CommunityModel;
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.models.ReviewsModel;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.example.newpetapp.repositories.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddPet extends AppCompatActivity {
    ImageView ivBack,ivImage;
    EditText tvType;
    EditText tvName, tvAge, TvBreed, interest;
    Button btnSave;

    String detail;
    List<ProfilePets> profilePetsList;
    Uri uri;

    public List<ReviewsModel> getReviewsModelList() {
        return reviewsModelList;
    }

    public void setReviewsModelList(List<ReviewsModel> reviewsModelList) {
        this.reviewsModelList = reviewsModelList;
    }

    List<ReviewsModel>reviewsModelList;
    FirebaseStorage storage;
    DatabaseHelper db;
    FirebaseAuth auth;

    ProgressDialog progressDialog;
    String postUserName,image;

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
        setContentView(R.layout.activity_add_pet);

        ivBack = findViewById(R.id.ivBackArrow);
        ivImage = findViewById(R.id.ivImage);
        tvType = findViewById(R.id.edtType);
        tvName = findViewById(R.id.edtName);
        tvAge = findViewById(R.id.edtAge);
        TvBreed = findViewById(R.id.edtBreed);
        interest = findViewById(R.id.edtInterest);
        btnSave = findViewById(R.id.btnSave);
        reviewsModelList = new ArrayList<>();
        postUserName =getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);


        db=new DatabaseHelper();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

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
            String n= tvName.getText().toString();
            String typ= tvType.getText().toString();
            String ag= tvAge.getText().toString();
            String bred= TvBreed.getText().toString();
            String inter=interest.getText().toString();

            if (n.isEmpty() || typ.isEmpty() || ag.isEmpty() || bred.isEmpty() || inter.isEmpty() || uri==null){
                Toast.makeText(this,"Please Enter All Fields!",Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.show();
                myPet(n,typ,ag,bred,inter);

            }
        });


    }
    private void myPet(String name, String type,String age,String breed,String interest) {
        StorageReference storageReference=storage.getReference().child("My Pets").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String uid=auth.getCurrentUser().getUid();
                        ProfilePets profilePets=new ProfilePets(task.getResult().toString(),name,type,age,breed,interest,reviewsModelList);
                        reviewsModelList.add(new ReviewsModel("hello", auth.getUid(), task.getResult().toString(), Utils.UserName,2));

                        db.addMyPets(profilePets,uid).addOnCompleteListener(registerTask -> {
                            if (registerTask.isSuccessful()) {
                                progressDialog.hide();

                                db.getMyPets(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        profilePetsList = new ArrayList<>();
                                        profilePetsList.clear();
                                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                            ProfilePets pets = snapshot1.getValue(ProfilePets.class);
                                            profilePetsList.add(pets);

                                        }
                                        CommunityModel communityModel = new CommunityModel(uid,image, ActivityAddPet.this.postUserName,profilePetsList);
                                        db.addNewCommunity(communityModel,uid);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Toast.makeText(ActivityAddPet.this, "Pet Added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityAddPet.this, ActivityProfile.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.hide();
                                Toast.makeText(ActivityAddPet.this, "Failed to add data to database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(ActivityAddPet.this, "Some thing Wrong"+e, Toast.LENGTH_SHORT).show();

            }
        });
    }
}