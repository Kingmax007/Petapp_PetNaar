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
import com.example.newpetapp.adapters.AdvicesAdapter;
import com.example.newpetapp.adapters.ClinicsAdapter;
import com.example.newpetapp.models.Advices;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdvice extends AppCompatActivity {
    ImageView ivBack, addAdvice;

    RecyclerView recyclerViewAdvice;

    AdvicesAdapter adapter;

    DatabaseHelper db;

    List<Advices>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        ivBack = findViewById(R.id.ivBackArrow);
        addAdvice = findViewById(R.id.ivAdd);
        recyclerViewAdvice=findViewById(R.id.recyclerViewAdvice);

        list=new ArrayList<>();
        db=new DatabaseHelper();


        ivBack.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityHome.class);
            startActivity(intent);
            finish();
        });

        db.getAdvices().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot getPlaces:snapshot.getChildren()){
                    Advices advices=getPlaces.getValue(Advices.class);
                    list.add(advices);
                }
                loadRecycleView();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addAdvice.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityAddAdvice.class);
            startActivity(intent);
        });

    }
    private void loadRecycleView() {
        recyclerViewAdvice.setLayoutManager(new LinearLayoutManager(ActivityAdvice.this));
        adapter = new AdvicesAdapter(this, list);
        recyclerViewAdvice.setAdapter(adapter);
        recyclerViewAdvice.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }
}