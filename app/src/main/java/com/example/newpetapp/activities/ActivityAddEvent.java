package com.example.newpetapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Events;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityAddEvent extends AppCompatActivity {
    ImageView ivBack, addImage;
    EditText edtTitle, edtLocation, edtDetail;
    Spinner date;
    Button btnSave;


    DatabaseHelper db;

    Uri uri;

    FirebaseStorage storage;

    ProgressDialog progressDialog;


    String selectedDate;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            addImage.setImageURI(uri);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        ivBack = findViewById(R.id.ivBackArrow);
        addImage = findViewById(R.id.ivCamera);
        edtTitle= findViewById(R.id.edtName);
        edtLocation = findViewById(R.id.edtlocation);
        edtDetail= findViewById(R.id.edtDetail);
        date = findViewById(R.id.edtDate);
        btnSave = findViewById(R.id.btnSave);

        db=new DatabaseHelper();
        storage=FirebaseStorage.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");
        progressDialog.setCancelable(false);


        addImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        // Populate spinner with dates
        List<String> dates = getFutureDates(6000);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(adapter);

        // Set listener for spinner item selection
        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate = parent.getItemAtPosition(position).toString();
                // Handle the selected date
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });

        btnSave.setOnClickListener(view -> {
            String name=edtTitle.getText().toString();
            String address=edtLocation.getText().toString();
            String selectDate =selectedDate;
            String details=edtDetail.getText().toString();
            if (name.isEmpty() || address.isEmpty() ||selectDate.isEmpty() || details.isEmpty()|| uri==null){
                Toast.makeText(this,"Please Enter All Fields!",Toast.LENGTH_SHORT).show();

            }
            else {
                progressDialog.show();
                eventsAdd(name,address,selectDate,details);
            }

        });
    }

    private void eventsAdd(String name, String address, String selectDate, String details) {
        StorageReference storageReference=storage.getReference().child("EventsImages").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Events events=new Events(task.getResult().toString(),name,selectDate,address,details);
                        db.addEvents(events).addOnCompleteListener(registerTask -> {
                            if (registerTask.isSuccessful()) {
                                progressDialog.hide();
                                Toast.makeText(ActivityAddEvent.this, "Event Added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityAddEvent.this, ActivityEvents.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.hide();
                                Toast.makeText(ActivityAddEvent.this, "Failed to add data to database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(ActivityAddEvent.this, "Some thing Wrong"+e, Toast.LENGTH_SHORT).show();

            }
        });

    }

    // Method to get future dates starting from today
    private List<String> getFutureDates(int numberOfDays) {
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        for (int i = 0; i < numberOfDays; i++) {
            dateList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1); // Increment calendar by 1 day
        }
        return dateList;
    }
}