package com.example.newpetapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newpetapp.R;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityCreateProfile extends AppCompatActivity {
    ImageView ivBack, ivProfile;
    EditText edtName, edtEmail, edtPass, edtConfirmPass;
    Button btnSave;

    FirebaseAuth auth;

    DatabaseHelper db;

    Uri uri;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            ivProfile.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        ivProfile = findViewById(R.id.ivProfile);
        ivBack = findViewById(R.id.ivBackArrow);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtConfirmPass = findViewById(R.id.edtConfPass);
        btnSave = findViewById(R.id.btnSave);

        auth=FirebaseAuth.getInstance();
        db=new DatabaseHelper();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

//        btnSave.setOnClickListener(view -> {
//            String name=edtName.getText().toString();
//            String email=edtEmail.getText().toString();
//            String password=edtPass.getText().toString();
//            String confirmPass=edtPass.getText().toString();
//
//            if (name.isEmpty() || email.isEmpty());
//
//        });






    }
}