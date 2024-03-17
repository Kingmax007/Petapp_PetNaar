package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddPet;
import com.example.newpetapp.activities.ActivityHome;
import com.example.newpetapp.adapters.ProfilePetsAdapter;
import com.example.newpetapp.models.ProfilePets;

import java.util.ArrayList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity {
    ImageView ivBack, ivProfile, ivEdit, btnAdd;

    TextView tvName, tvEmail;
    RecyclerView recyclerView;

    List<ProfilePets>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.ivBackArrow);
        ivProfile = findViewById(R.id.profile_picture);
        ivEdit = findViewById(R.id.ivEdit);
        btnAdd = findViewById(R.id.ivAdd);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvMail);
        recyclerView = findViewById(R.id.recyclerViewProfile);

        list=new ArrayList<>();
        list.add(new ProfilePets(R.drawable.iv_row_1,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));
        list.add(new ProfilePets(R.drawable.iv_row_2,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));
        list.add(new ProfilePets(R.drawable.iv_row_3,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));
        list.add(new ProfilePets(R.drawable.iv_row_4,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));
        list.add(new ProfilePets(R.drawable.iv_row_5,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));
        list.add(new ProfilePets(R.drawable.iv_row_2,"Australian Shepherd","Intelligent, Hard worki...",R.drawable.ic_menu2));

        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(new ProfilePetsAdapter(this,list));
        recyclerView.setHasFixedSize(true);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}