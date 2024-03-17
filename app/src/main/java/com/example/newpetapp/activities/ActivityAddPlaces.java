package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;

public class ActivityAddPlaces extends AppCompatActivity {
    ImageView ivBack, addImage;
    EditText edtName, edtLocation;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
        ivBack = findViewById(R.id.ivBackArrow);
        addImage = findViewById(R.id.ivCamera);
        edtName= findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtlocation);
        btnSave = findViewById(R.id.btnSave);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityPetFriendlyPlaces.class);
                startActivity(intent);
            }
        });

    }
}