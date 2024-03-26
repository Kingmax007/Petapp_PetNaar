package com.example.newpetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Advices;
import com.example.newpetapp.repositories.DatabaseHelper;

public class ActivityAddAdvice extends AppCompatActivity {
    ImageView ivBack;
    EditText edtTitle, edtDetail;
    Button btnSave;

    DatabaseHelper db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advice);
        ivBack = findViewById(R.id.ivBackArrow);
        edtTitle = findViewById(R.id.edtTitle);
        edtDetail = findViewById(R.id.edtDetail);
        btnSave = findViewById(R.id.btnSave);

        db=new DatabaseHelper();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait....");
        progressDialog.setCancelable(false);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

        btnSave.setOnClickListener(view -> {
            String name=edtTitle.getText().toString();
            String details=edtDetail.getText().toString();
            if (name.isEmpty()|| details.isEmpty()){
                Toast.makeText(this, "Please Enter all details!", Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.show();
                advicesAdd(name,details);
            }

        });
    }

    private void advicesAdd(String name, String details) {

        Advices advices=new Advices(name,details);

        db.addAdvice(advices).addOnSuccessListener(suc ->{
            progressDialog.hide();
            Toast.makeText(this, "Advice Added!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,ActivityAdvice.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(er ->{
            progressDialog.hide();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        });

    }
}