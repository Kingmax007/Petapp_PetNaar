package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddClinic;
import com.example.newpetapp.activities.ActivityHome;

public class ActivityPetClinics extends AppCompatActivity {
    ImageView ivBack, addClinic;
    RecyclerView recyclerView;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_clinics);
        ivBack = findViewById(R.id.ivBackArrow);
        addClinic = findViewById(R.id.ivAdd);
        recyclerView= findViewById(R.id.recyclerView);
        search = findViewById(R.id.search);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(intent);
            }
        });

        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddClinic.class);
                startActivity(intent);
            }
        });
    }
}