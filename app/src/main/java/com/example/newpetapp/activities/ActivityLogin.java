package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    RelativeLayout btnLogin;
    TextView tvCreateAcc;

    FirebaseAuth auth;

    DatabaseHelper db;

    String email="abcd@gmail.com";
    String password="1234567";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAcc = findViewById(R.id.tvCreateAcc);

        auth=FirebaseAuth.getInstance();
        db=new DatabaseHelper();

        tvCreateAcc.setOnClickListener(view -> {
            Intent intent=new Intent(ActivityLogin.this, ActivityCreateProfile.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            String e=edtEmail.getText().toString();
            String pass=edtPassword.getText().toString();

            if (e.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (e.equals(email) && pass.equals(password)){
                Intent intent=new Intent(this, ActivityHome.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this,"Email or Password is incorrect",Toast.LENGTH_SHORT).show();
            }

        });
    }
}