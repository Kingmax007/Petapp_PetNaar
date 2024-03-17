package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddReview;
import com.example.newpetapp.activities.ActivityCommunity;

public class ActivityPetDetails extends AppCompatActivity {
ImageView ivBack;
RecyclerView recyclerView;
TextView tvTopName, tvName, tvAge, tvBreed, tvInterest, tvReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ivBack = findViewById(R.id.ivBackArrow);
        tvTopName = findViewById(R.id.tvName);
        recyclerView= findViewById(R.id.recyclerView);
        tvName = findViewById(R.id.tvName2);
        tvAge = findViewById(R.id.tvAge);
        tvBreed= findViewById(R.id.tvBreed);
        tvInterest = findViewById(R.id.tvInterest);
        tvReview= findViewById(R.id.tvReview);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCommunity.class);
                startActivity(intent);
            }
        });

        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddReview.class);
                startActivity(intent);
            }
        });
    }
}