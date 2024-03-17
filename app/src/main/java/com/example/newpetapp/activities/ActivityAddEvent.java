package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.newpetapp.R;

public class ActivityAddEvent extends AppCompatActivity {
    ImageView ivBack, addImage;
    EditText edtTitle, edtLocation, edtDetail;
    Spinner date;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        ivBack = findViewById(R.id.ivBackArrow);
        addImage = findViewById(R.id.ivCamera);
        edtTitle= findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtlocation);
        edtDetail= findViewById(R.id.edtDetail);
        date = findViewById(R.id.edtDate);
        btnSave = findViewById(R.id.btnSave);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityEvents.class);
                startActivity(intent);
            }
        });
    }
}