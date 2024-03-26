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
import com.example.newpetapp.adapters.CommunityAdapter;
import com.example.newpetapp.models.CommunityModel;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.models.Users;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.example.newpetapp.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityCommunity extends AppCompatActivity {
    ImageView ivBack, ivProfile;
    EditText search;
    DatabaseHelper db;
    List<CommunityModel>list;
    RecyclerView recyclerView;
    CommunityAdapter communityAdapter;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ivBack = findViewById(R.id.ivBackArrow);
        ivProfile = findViewById(R.id.ivProfile);
        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerview);

        auth=FirebaseAuth.getInstance();
        db=new DatabaseHelper();
        list = new ArrayList<>();


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


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        communityAdapter=new CommunityAdapter(list,ActivityCommunity.this);
        recyclerView.setAdapter(communityAdapter);
        db.getAllCommunities().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                    CommunityModel user =snapshot1.getValue(CommunityModel.class);
                    String key=snapshot1.getKey();
                    user.setKey(key);
                    user.getUid();
                    list.add(user);
                    communityAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void filterList(String searchText) {
        List<CommunityModel> filteredList = new ArrayList<>();

        for (CommunityModel product : list) {
            if (product.getPostUserName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(product);
            }
        }

        // Update the adapter with the filtered list
        communityAdapter.filterList(filteredList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,ActivityHome.class);
        startActivity(intent);
        finish();
    }
}