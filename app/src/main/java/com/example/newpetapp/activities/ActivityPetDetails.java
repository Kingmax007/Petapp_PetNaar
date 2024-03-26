package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddReview;
import com.example.newpetapp.activities.ActivityCommunity;
import com.example.newpetapp.adapters.ReviewAdapter;
import com.example.newpetapp.models.ProfilePets;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ActivityPetDetails extends AppCompatActivity {
ImageView ivBack,ivImage;
RecyclerView recyclerView;
TextView tvTopName, tvName, tvAge, tvBreed, tvInterest, tvReview,name;
    List<ProfilePets>list;
    int  position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ivBack = findViewById(R.id.ivBackArrow);
        ivImage = findViewById(R.id.ivImage);
        tvTopName = findViewById(R.id.tvName);
        recyclerView= findViewById(R.id.recyclerView);
        tvName = findViewById(R.id.tvName2);
        name = findViewById(R.id.name);
        tvAge = findViewById(R.id.tvAge);
        tvBreed= findViewById(R.id.tvBreed);
        tvInterest = findViewById(R.id.tvInterest);
        tvReview= findViewById(R.id.tvReview);


        list = (List<ProfilePets>) getIntent().getSerializableExtra("petsList");
        position = getIntent().getIntExtra("position",-1);
        ProfilePets pet = list.get(position);
        Picasso.get().load(pet.getImage()).into(ivImage);
        tvName.setText(pet.getName());
        tvTopName.setText(pet.getName());
        name.setText(pet.getName());
        tvAge.setText(pet.getAge());
        tvBreed.setText(pet.getBreed());
        tvInterest.setText(pet.getInterest());

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new ReviewAdapter(pet.getReviewsModelsList(),getApplicationContext()));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCommunity.class);
                startActivity(intent);
                finish();
            }
        });

        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddReview.class);
                intent.putExtra("petsList", (Serializable) list);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ActivityCommunity.class);
        startActivity(intent);
        finish();

    }
}