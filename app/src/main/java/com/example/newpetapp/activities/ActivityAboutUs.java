package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ActivityAboutUs extends AppCompatActivity {
    ImageView ivBack, ivProfile;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ivBack = findViewById(R.id.ivBackArrow);
        ivProfile = findViewById(R.id.ivProfile);

        auth=FirebaseAuth.getInstance();

        ivBack.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityHome.class);
            startActivity(intent);
            finish();
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
                startActivity(intent);
            }
        });

        getProfileData();

    }

    private void getProfileData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        Picasso.get().load(user.getUrl()).into(ivProfile);
                    }
                    else {
                        ivProfile.setImageResource(R.drawable.ic_profile);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }


}