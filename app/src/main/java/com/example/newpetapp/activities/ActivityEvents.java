package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.adapters.ClinicsAdapter;
import com.example.newpetapp.adapters.EventsAdapter;
import com.example.newpetapp.models.Events;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityEvents extends AppCompatActivity {
    ImageView ivBack, addEvent;

    RecyclerView recyclerViewEvents;

    EventsAdapter adapter;

    List<Events> list;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ivBack = findViewById(R.id.ivBackArrow);
        addEvent = findViewById(R.id.ivAdd);
        recyclerViewEvents=findViewById(R.id.recyclerViewEvents);

        list=new ArrayList<>();
        db=new DatabaseHelper();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(ActivityEvents.this,ActivityHome.class);
            startActivity(intent);
            finish();
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddEvent.class);
                startActivity(intent);
            }
        });

        db.getEvents().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot getPlaces:snapshot.getChildren()){
                    Events events=getPlaces.getValue(Events.class);
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
    private void loadRecycleView() {
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(ActivityEvents.this));
        adapter = new EventsAdapter(this, list);
        recyclerViewEvents.setAdapter(adapter);
        recyclerViewEvents.setHasFixedSize(true);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }
}