package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.newpetapp.R;

public class ActivityAddReview extends AppCompatActivity {
    ImageView ivBack;
    RatingBar addRating;
    EditText edtDetail;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ivBack = findViewById(R.id.ivBackArrow);
        edtDetail = findViewById(R.id.detail);
        addRating = findViewById(R.id.rating);
        btnAdd = findViewById(R.id.btnReview);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}