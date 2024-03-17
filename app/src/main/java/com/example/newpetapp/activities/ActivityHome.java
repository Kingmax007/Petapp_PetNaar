package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newpetapp.R;

public class ActivityHome extends AppCompatActivity {
    ImageView menu, profile;
    Button btnJoin;
     DrawerLayout drawerLayout;

     ImageView iv_close;

     TextView tv_community,tv_places,tv_events,tv_advice,tv_About,tv_Contact,tv_faq,tv_follow,tv_signOut;
     Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menu = findViewById(R.id.ivMenu);
        profile = findViewById(R.id.ivProfile);
        btnJoin = findViewById(R.id.btnJoin);
        drawerLayout=findViewById(R.id.drawer_layout);
        iv_close=findViewById(R.id.close);
        tv_community=findViewById(R.id.community);
        tv_places=findViewById(R.id.places);
        tv_events=findViewById(R.id.events);
        tv_advice=findViewById(R.id.advice);
        tv_About=findViewById(R.id.tvAbout);
        tv_Contact=findViewById(R.id.tvContact);
        tv_faq=findViewById(R.id.faq);
        tv_follow=findViewById(R.id.follow);
        tv_signOut=findViewById(R.id.signOut);
        btnChange=findViewById(R.id.btnChange);

        btnChange.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityChangeLanguage.class);
            startActivity(intent);
        });

        tv_community.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityCommunity.class);
            startActivity(intent);
        });

        tv_places.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityPetFriendlyPlaces.class);
            startActivity(intent);
        });

        tv_events.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityEvents.class);
            startActivity(intent);
        });

        tv_advice.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityAdvice.class);
            startActivity(intent);
        });

        tv_About.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityAboutUs.class);
            startActivity(intent);
        });

        tv_faq.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityFAQs.class);
            startActivity(intent);
        });

        tv_follow.setOnClickListener(view -> {
            Intent intent=new Intent(this, ActivityAddReview.class);
            startActivity(intent);
        });



        iv_close.setOnClickListener(view -> {
            closeDrawer(drawerLayout);
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityProfile.class);
                startActivity(intent);
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCreateProfile.class);
                startActivity(intent);
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}