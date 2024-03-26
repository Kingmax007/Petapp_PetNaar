package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ActivityFAQs extends AppCompatActivity {

    ImageView ivBack, ivProfile, dropDown, dropDown2, dropDown3, dropDown4, dropDown5;
    TextView answer, answer2, answer3, answer4, answer5;
    boolean isDropDownVisible = false;
    boolean isDropDown2Visible = false;
    boolean isDropDown3Visible = false;
    boolean isDropDown4Visible = false;
    boolean isDropDown5Visible = false;


    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        ivBack = findViewById(R.id.ivBackArrow);
        ivProfile = findViewById(R.id.ivProfile);
        dropDown = findViewById(R.id.dropDown);
        dropDown2 = findViewById(R.id.dropDown2);
        dropDown3 = findViewById(R.id.dropDown3);
        dropDown4 = findViewById(R.id.dropDown4);
        dropDown5 = findViewById(R.id.dropDown5);
        answer = findViewById(R.id.answer);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        answer5 = findViewById(R.id.answer5);

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

        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropDownVisible) {
                    answer.setVisibility(View.GONE);
                } else {
                    answer.setVisibility(View.VISIBLE);
                }
                isDropDownVisible = !isDropDownVisible;
            }
        });

        dropDown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropDown2Visible) {
                    answer2.setVisibility(View.GONE);
                } else {
                    answer2.setVisibility(View.VISIBLE);
                }
                isDropDown2Visible = !isDropDown2Visible;
            }
        });

        dropDown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropDown3Visible) {
                    answer3.setVisibility(View.GONE);
                } else {
                    answer3.setVisibility(View.VISIBLE);
                }
                isDropDown3Visible = !isDropDown3Visible;
            }
        });

        dropDown4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropDown4Visible) {
                    answer4.setVisibility(View.GONE);
                } else {
                    answer4.setVisibility(View.VISIBLE);
                }
                isDropDown4Visible = !isDropDown4Visible;
            }
        });

        dropDown5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDropDown5Visible) {
                    answer5.setVisibility(View.GONE);
                } else {
                    answer5.setVisibility(View.VISIBLE);
                }
                isDropDown5Visible = !isDropDown5Visible;
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }
}
