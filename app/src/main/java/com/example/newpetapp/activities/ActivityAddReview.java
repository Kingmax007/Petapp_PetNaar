package com.example.newpetapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newpetapp.R;
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.models.ReviewsModel;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.example.newpetapp.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityAddReview extends AppCompatActivity {
    ImageView ivBack;
    RatingBar addRating;
    EditText edtDetail;
    Button btnAdd;

    DatabaseHelper databaseHelper;
    FirebaseAuth auth;
    List<ProfilePets>list;
    int  position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        auth =FirebaseAuth.getInstance();
        ivBack = findViewById(R.id.ivBackArrow);
        edtDetail = findViewById(R.id.detail);
        addRating = findViewById(R.id.rating);
        btnAdd = findViewById(R.id.btnReview);
        list = (List<ProfilePets>) getIntent().getSerializableExtra("petsList");
        position = getIntent().getIntExtra("position",-1);
        databaseHelper = new DatabaseHelper();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePets profilePets = list.get(position);
                List<ReviewsModel>reviewsModelList=profilePets.getReviewsModelsList();
                String reviewDetail = edtDetail.getText().toString();
                float rating=addRating.getRating();
                reviewsModelList.add(new ReviewsModel(reviewDetail, auth.getUid(), Utils.CurrentUserImageUrl,Utils.UserName,rating));
                profilePets.setReviewsModelsList(reviewsModelList);
                list.remove(position);
                list.add(position,profilePets);


                databaseHelper.updateCommunityPetsList(auth.getUid(),list);

                Intent intent=new Intent(ActivityAddReview.this,ActivityPetDetails.class);
                intent.putExtra("petsList", (Serializable) list);
                intent.putExtra("position",  position);
                startActivity(intent);
                finish();

            }
        });
    }


}
