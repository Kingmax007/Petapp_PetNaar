package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.newpetapp.R;

public class ActivityAddPet extends AppCompatActivity {
    ImageView ivBack;
         RelativeLayout ivAddImage;
    Spinner type;
    EditText name, age, breed, interest;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        ivBack = findViewById(R.id.ivBackArrow);
        ivAddImage = findViewById(R.id.ivAddImage);
        type = findViewById(R.id.edtType);
        name = findViewById(R.id.edtName);
        age = findViewById(R.id.edtAge);
        breed = findViewById(R.id.edtBreed);
        interest = findViewById(R.id.edtInterest);
        btnSave = findViewById(R.id.btnSave);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
                startActivity(intent);
            }
        });
    }
}