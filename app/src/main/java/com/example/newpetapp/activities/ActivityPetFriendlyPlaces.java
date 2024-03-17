package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddPlaces;
import com.example.newpetapp.activities.ActivityHome;

public class ActivityPetFriendlyPlaces extends AppCompatActivity {
    ImageView ivBack, addPlace;
    RecyclerView recyclerView;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_friendly_places);
        ivBack = findViewById(R.id.ivBackArrow);
        addPlace = findViewById(R.id.ivAdd);
        recyclerView= findViewById(R.id.recyclerView);
        search = findViewById(R.id.search);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }
}