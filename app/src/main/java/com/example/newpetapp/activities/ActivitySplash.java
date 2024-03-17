package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newpetapp.R;

public class ActivitySplash extends AppCompatActivity {


    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnStart=findViewById(R.id.btnStart);


        btnStart.setOnClickListener(view -> {
            Intent intent=new Intent(ActivitySplash.this,ActivityLogin.class);
            startActivity(intent);
            finish();
        });
    }
}