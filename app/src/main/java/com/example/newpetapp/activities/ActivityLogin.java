package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.example.newpetapp.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLogin extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    RelativeLayout btnLogin;
    TextView tvCreateAcc;

    FirebaseAuth auth;

    DatabaseHelper db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAcc = findViewById(R.id.tvCreateAcc);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);

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
                Toast.makeText(ActivityLogin.this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.show();
                loginUser(e,pass);
            }



        });
    }


    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressDialog.hide();
                        getProfileData();
                        Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (task.getException() != null && task.getException().getMessage() != null) {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage.contains("password is invalid")) {
                               progressDialog.hide();
                                Toast.makeText(ActivityLogin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            } else if (errorMessage.contains("no user record")) {
                                progressDialog.hide();
                                Toast.makeText(ActivityLogin.this, "User not found. Please sign up.", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.hide();
                                Toast.makeText(ActivityLogin.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.hide();
                            Toast.makeText(ActivityLogin.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getProfileData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        Utils.UserName = user.getName();
                        Utils.CurrentUserImageUrl=user.getUrl();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}