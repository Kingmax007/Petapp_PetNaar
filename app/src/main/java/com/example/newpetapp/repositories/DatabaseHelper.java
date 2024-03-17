package com.example.newpetapp.repositories;

import com.example.newpetapp.models.Users;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    DatabaseReference databaseReference;

    public  String users="Users";
    public DatabaseHelper() {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(users);
    }


    Task<Void> addUsers(Users users){return databaseReference.push().setValue(users);}
}
