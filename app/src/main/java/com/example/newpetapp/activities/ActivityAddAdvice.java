package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;

public class ActivityAddAdvice extends AppCompatActivity {
    ImageView ivBack;
    EditText edtTitle, edtDetail;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advice);
        ivBack = findViewById(R.id.ivBackArrow);
        edtTitle = findViewById(R.id.edtTitle);
        edtDetail = findViewById(R.id.edtDetail);

        btnSave = findViewById(R.id.btnSave);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdvice.class);
                startActivity(intent);
            }
        });
    }
}