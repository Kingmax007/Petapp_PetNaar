package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddPet;
import com.example.newpetapp.activities.ActivityHome;
import com.example.newpetapp.adapters.ClinicsAdapter;
import com.example.newpetapp.adapters.ProfilePetsAdapter;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity {
    ImageView ivBack, ivProfile, ivEdit, btnAdd;

    TextView tvName, tvEmail,tvLogout;
    RecyclerView recyclerView;

    List<ProfilePets> list;

    ProfilePetsAdapter adapter;

    DatabaseHelper db;
    String image,username;
    FirebaseAuth auth;
    public static String uid;

    public static  String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.ivBackArrow);
        ivProfile = findViewById(R.id.profile_picture);
        ivEdit = findViewById(R.id.ivEdit);
        btnAdd = findViewById(R.id.ivAdd);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvMail);
        tvLogout=findViewById(R.id.tvLogout);
        recyclerView = findViewById(R.id.recyclerViewProfile);

        db = new DatabaseHelper();
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new ProfilePetsAdapter(this, list));
        recyclerView.setHasFixedSize(true);



        tvLogout.setOnClickListener(view -> {
            Toast.makeText(this,"You have been signed out",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            finish();

        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityProfile.this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityAddPet.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("image", image);
            startActivity(intent);
        });

        ivEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("name", tvName.getText().toString());
            intent.putExtra("image", image);
            startActivity(intent);
        });

        if (auth.getCurrentUser() != null) {
            uid = auth.getCurrentUser().getUid();
            String email = auth.getCurrentUser().getEmail();
            tvEmail.setText(email);

            db.getMyPets(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot getPlaces : snapshot.getChildren()) {
                        ProfilePets profilePets = getPlaces.getValue(ProfilePets.class);
                        key= getPlaces.getKey();
                        profilePets.setKey(key);
                        list.add(profilePets);
                    }
                    loadRecycleView();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });

            getProfileData();
        } else {

            Toast.makeText(ActivityProfile.this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }

    }
        private void loadRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityProfile.this));
        adapter = new ProfilePetsAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    private void getProfileData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        username=user.getName();
                        tvName.setText(user.getName());
                        image=user.getUrl();
                        Picasso.get().load(user.getUrl()).into(ivProfile);
                    }
                    else {
                        ivProfile.setImageResource(R.drawable.ic_profile);
                        tvName.setText("");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ivProfile.setImageResource(R.drawable.ic_profile);
                tvName.setText("");
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