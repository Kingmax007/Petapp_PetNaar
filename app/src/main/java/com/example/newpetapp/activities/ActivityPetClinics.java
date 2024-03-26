package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddClinic;
import com.example.newpetapp.activities.ActivityHome;
import com.example.newpetapp.adapters.ClinicsAdapter;
import com.example.newpetapp.adapters.PetPlacesAdapter;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityPetClinics extends AppCompatActivity {
    ImageView ivBack, addClinic;
    RecyclerView recyclerView;
    EditText search;

    ClinicsAdapter adapter;
    List<PetClinics> list;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_clinics);
        ivBack = findViewById(R.id.ivBackArrow);
        addClinic = findViewById(R.id.ivAdd);
        recyclerView= findViewById(R.id.recyclerView);
        search = findViewById(R.id.search);


        list=new ArrayList<>();
        db=new DatabaseHelper();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on the entered text
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddClinic.class);
                startActivity(intent);
            }
        });


        db.getClinics().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot getPlaces:snapshot.getChildren()){
                    PetClinics clinics=getPlaces.getValue(PetClinics.class);
                    list.add(clinics);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityPetClinics.this));
        adapter = new ClinicsAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
    private void filterList(String searchText) {
        List<PetClinics> filteredList = new ArrayList<>();

        for (PetClinics clinics : list) {
            if (clinics.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(clinics);
            }
        }

        // Update the adapter with the filtered list
        adapter.filterList(filteredList);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }
}