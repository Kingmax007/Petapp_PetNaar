package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.LanguageManager;
import com.example.newpetapp.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitySplash extends AppCompatActivity {


    Button btnStart;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();
        btnStart=findViewById(R.id.btnStart);

        LanguageManager.applyLanguage(this);
        btnStart.setOnClickListener(view -> {
            if (auth.getCurrentUser() != null) {
                redirectToHome();
            }
            else {
                Intent intent=new Intent(ActivitySplash.this,ActivityLogin.class);
                startActivity(intent);
                finish();
            }

        });
    }
    private void redirectToHome() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        Utils.UserName = user.getName();
                        Utils.CurrentUserImageUrl = user.getUrl();
                    }
                    // Proceed to home activity
                    startActivity(new Intent(ActivitySplash.this, ActivityHome.class));
                    finish();
                } else {
                    // If user data doesn't exist, proceed to login activity
                    startActivity(new Intent(ActivitySplash.this, ActivityLogin.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }
}