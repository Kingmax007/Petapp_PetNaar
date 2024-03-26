package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.adapters.HomeClinicsAdapter;
import com.example.newpetapp.adapters.HomeCommunityAdapter;
import com.example.newpetapp.adapters.HomeEventsAdapter;
import com.example.newpetapp.adapters.HomePlacesAdapter;
import com.example.newpetapp.models.CommunityModel;
import com.example.newpetapp.models.Events;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.example.newpetapp.repositories.LanguageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity {
    ImageView menu, profile;
    Button btnJoin;
    DrawerLayout drawerLayout;

    ImageView iv_close;

    TextView tv_community, tv_places, tv_events, tv_advice,
            tv_About, tv_faq, tv_signOut, tv_clinics,language,viewAll,viewAll2,viewAll3,viewAll4;

    FirebaseAuth auth;
    String uid;
    RecyclerView recyclerViewEvents, recyclerViewPlaces, recyclerViewClinics,recyclerViewCommunity;
    HomeEventsAdapter adapter;
    HomePlacesAdapter homePlacesAdapter;

    HomeClinicsAdapter homeClinicsAdapter;

    HomeCommunityAdapter communityAdapter;
    List<Events> list;
    List<PetPlaces> petPlacesList;
    List<PetClinics> petClinicsList;
    List<CommunityModel> communityModels;
    DatabaseHelper db;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu = findViewById(R.id.ivMenu);
        profile = findViewById(R.id.ivProfile);
        btnJoin = findViewById(R.id.btnJoin);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_close = findViewById(R.id.close);
        tv_community = findViewById(R.id.community);
        tv_places = findViewById(R.id.places);
        tv_events = findViewById(R.id.events);
        tv_advice = findViewById(R.id.advice);
        tv_About = findViewById(R.id.tvAbout);
        tv_faq = findViewById(R.id.faq);
        tv_signOut = findViewById(R.id.signOut);
        tv_clinics = findViewById(R.id.clinics);
        language=findViewById(R.id.language);
        viewAll=findViewById(R.id.viewAll);
        viewAll2=findViewById(R.id.viewAll2);
        viewAll3=findViewById(R.id.viewAll3);
        viewAll4=findViewById(R.id.viewAll4);

//        LanguageManager.applyLanguage(this);

        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        recyclerViewPlaces = findViewById(R.id.recyclerViewPlaces);
        recyclerViewClinics = findViewById(R.id.recyclerViewClinics);
        recyclerViewCommunity=findViewById(R.id.recyclerViewCommunity);
        scrollView = findViewById(R.id.scrollView);

        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.setSmoothScrollingEnabled(true);


        list = new ArrayList<>();
        petPlacesList = new ArrayList<>();
        petClinicsList = new ArrayList<>();
        communityModels=new ArrayList<>();
        db = new DatabaseHelper();

        getEventsData();
        getPlaces();
        getClinics();
        getCommunities();



        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();

        getProfileData();



        viewAll.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityCommunity.class);
            startActivity(intent);

        });
        viewAll2.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityEvents.class);
            startActivity(intent);

        });
        viewAll3.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityPetFriendlyPlaces.class);
            startActivity(intent);

        });
        viewAll4.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityPetClinics.class);
            startActivity(intent);

        });

       language.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityChangeLanguage.class);
            startActivity(intent);
            finish();
        });

        tv_community.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityCommunity.class);
            startActivity(intent);
        });

        tv_places.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityPetFriendlyPlaces.class);
            startActivity(intent);
        });

        tv_events.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityEvents.class);
            startActivity(intent);
        });

        tv_advice.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityAdvice.class);
            startActivity(intent);
        });

        tv_About.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityAboutUs.class);
            startActivity(intent);
        });

        tv_faq.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityFAQs.class);
            startActivity(intent);
        });


        tv_clinics.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityPetClinics.class);
            startActivity(intent);
        });


        iv_close.setOnClickListener(view -> {
            closeDrawer(drawerLayout);
        });

        tv_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                startActivity(intent);
                finish();
//                closeDrawer(drawerLayout);
//                profile.setImageResource(R.drawable.ic_profile);
                Toast.makeText(ActivityHome.this, "You have been signed out", Toast.LENGTH_SHORT).show();

            }
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

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }


    private void getProfileData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        Picasso.get().load(user.getUrl()).into(profile);
                    }
                    else {
                           profile.setImageResource(R.drawable.ic_profile);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }


    private void getEventsData() {
        db.getEvents().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot getPlaces : snapshot.getChildren()) {
                    Events events = getPlaces.getValue(Events.class);
                    list.add(events);
                }
                loadRecycleView();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getPlaces() {
        db.getPlaces().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petPlacesList.clear();
                for (DataSnapshot getPlaces : snapshot.getChildren()) {
                    PetPlaces places = getPlaces.getValue(PetPlaces.class);
                    petPlacesList.add(places);
                }
                loadRecycleViewPetPlaces();
                homePlacesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getClinics() {
        db.getClinics().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petClinicsList.clear();
                for (DataSnapshot getPlaces : snapshot.getChildren()) {
                    PetClinics clinics = getPlaces.getValue(PetClinics.class);
                    petClinicsList.add(clinics);
                }
                loadRecycleViewPetClinics();
                homeClinicsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCommunities(){
        db.getAllCommunities().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                communityModels.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    CommunityModel communityModel =snapshot1.getValue(CommunityModel.class);
                    communityModels.add(communityModel);

                }
                loadRecyclerViewCommunity();
                communityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadRecycleView() {
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(ActivityHome.this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeEventsAdapter(this, list);
        recyclerViewEvents.setAdapter(adapter);
        recyclerViewEvents.setHasFixedSize(true);
    }

    private void loadRecycleViewPetPlaces() {
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(ActivityHome.this, LinearLayoutManager.HORIZONTAL, false));
        homePlacesAdapter = new HomePlacesAdapter(this, petPlacesList);
        recyclerViewPlaces.setAdapter(homePlacesAdapter);
        recyclerViewPlaces.setHasFixedSize(true);
    }

    private void loadRecycleViewPetClinics() {
        recyclerViewClinics.setLayoutManager(new LinearLayoutManager(ActivityHome.this, LinearLayoutManager.HORIZONTAL, false));
        homeClinicsAdapter = new HomeClinicsAdapter(this, petClinicsList);
        recyclerViewClinics.setAdapter(homeClinicsAdapter);
        recyclerViewClinics.setHasFixedSize(true);
    }

    private void loadRecyclerViewCommunity() {
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(ActivityHome.this, LinearLayoutManager.HORIZONTAL, false));
        communityAdapter = new HomeCommunityAdapter(ActivityHome.this,communityModels);
        recyclerViewCommunity.setAdapter(communityAdapter);
        recyclerViewCommunity.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Apply selected language whenever the activity is resumed
        LanguageManager.applyLanguage(this);
    }
}