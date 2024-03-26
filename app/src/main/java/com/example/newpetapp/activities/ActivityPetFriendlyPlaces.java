package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.newpetapp.activities.ActivityAddPlaces;
import com.example.newpetapp.activities.ActivityHome;
import com.example.newpetapp.adapters.PetPlacesAdapter;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivityPetFriendlyPlaces extends AppCompatActivity {
    ImageView ivBack, addPlace;
    RecyclerView recyclerView;
    EditText search;

    PetPlacesAdapter adapter;
    List<PetPlaces>list;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_friendly_places);
        ivBack = findViewById(R.id.ivBackArrow);
        addPlace = findViewById(R.id.ivAdd);
        recyclerView= findViewById(R.id.recyclerViewPlaces);
        search = findViewById(R.id.search);

        list=new ArrayList<>();
        db=new DatabaseHelper();



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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityPetFriendlyPlaces.this,ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });

        addPlace.setOnClickListener(view -> {
            Intent intent=new Intent(this,ActivityAddPlaces.class);
            startActivity(intent);
        });


        db.getPlaces().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot getPlaces:snapshot.getChildren()){
                    PetPlaces places=getPlaces.getValue(PetPlaces.class);
                    list.add(places);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityPetFriendlyPlaces.this));
        adapter = new PetPlacesAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
    private void filterList(String searchText) {
        List<PetPlaces> filteredList = new ArrayList<>();

        for (PetPlaces product : list) {
            if (product.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(product);
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