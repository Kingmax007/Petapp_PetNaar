package com.example.newpetapp.repositories;

import androidx.annotation.NonNull;

import com.example.newpetapp.models.Advices;
import com.example.newpetapp.models.CommunityModel;
import com.example.newpetapp.models.Events;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.models.ReviewsModel;
import com.example.newpetapp.models.Users;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    DatabaseReference databaseReference;
    DatabaseReference placesReferences;
    DatabaseReference clinicReference;
    DatabaseReference eventsReference;
    DatabaseReference advicesReference;
    DatabaseReference myPetsReference;
    DatabaseReference communityReferences;

    public String users = "Users";
    public String places = "Pet Places";
    public String clinics = "Pet Clinics";
    public String events = "Events";
    public String advices = "Advices";
    public String myPets = "My Pets";

    public DatabaseHelper() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(users);
        placesReferences = database.getReference(places);
        clinicReference = database.getReference(clinics);
        eventsReference = database.getReference(events);
        advicesReference = database.getReference(advices);
        myPetsReference = database.getReference(myPets);
        communityReferences = database.getReference("Community");
    }

    public Task<Void> addUsers(Users users, String uid) {
        return databaseReference.child(uid).setValue(users);
    }

    public Task<Void> addPetPlaces(PetPlaces places) {
        return placesReferences.push().setValue(places);
    }

    public Task<Void> addPetClinics(PetClinics petClinics) {
        return clinicReference.push().setValue(petClinics);
    }

    public Task<Void> addEvents(Events events) {
        return eventsReference.push().setValue(events);
    }

    public Task<Void> addAdvice(Advices advices) {
        return advicesReference.push().setValue(advices);
    }

    public Task<Void> addMyPets(ProfilePets profilePets, String uid) {
        return myPetsReference.child(uid).push().setValue(profilePets);
    }

    public Task<Void> addNewCommunity(CommunityModel communityModel, String uid) {
        return communityReferences.child(uid).setValue(communityModel);
    }

    public Query getPlaces() {
        return placesReferences.orderByKey();
    }

    public Query getClinics() {
        return clinicReference.orderByKey();
    }

    public Query getEvents() {
        return eventsReference.orderByKey();
    }

    public Query getAdvices() {
        return advicesReference.orderByKey();
    }

    public Query getMyPets(String userId) {
        return myPetsReference.child(userId).orderByKey();
    }

//   // public Query getAllUser() {
//        return databaseReference.orderByKey();
//    }

    public Query getAllCommunities() {
        return communityReferences.orderByKey();
    }



    public Task<Void> updateUserProfile(Users users, String uid) {
        DatabaseReference userRef = databaseReference.child(uid);
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("name", users.getName());
        userValues.put("url", users.getUrl());
        return userRef.updateChildren(userValues);
    }

    public Task<Void> updateMyPetProfile(ProfilePets profilePets, String uid, String petKey) {
        DatabaseReference petRef = myPetsReference.child(uid).child(petKey);
        Map<String, Object> petValues = new HashMap<>();
        petValues.put("name", profilePets.getName());
        petValues.put("type", profilePets.getType());
        petValues.put("age", profilePets.getAge());
        petValues.put("breed", profilePets.getBreed());
        petValues.put("interest", profilePets.getInterest());
        if (profilePets.getImage() != null) {
            // If image is provided, update image URL as well
            petValues.put("image", profilePets.getImage());
        }
        return petRef.updateChildren(petValues);
    }

    public Task<Void> updateCommunityPetsList(String uid, List<ProfilePets> updatedPetsList) {
        DatabaseReference communityRef = communityReferences.child(uid).child("list");
        return communityRef.setValue(updatedPetsList);
    }
    public Task<Void> updateReviewsList(String uid, List<ReviewsModel> reviewsModels) {
        DatabaseReference communityRef = communityReferences.child(uid).child("list").child("reviewsModelsList");
        return communityRef.setValue(reviewsModels);
    }

    // In DatabaseHelper.java
    public Task<Void> deleteMyPet(String uid, String petKey) {
        return myPetsReference.child(uid).child(petKey).removeValue();
    }

    public Task<Void> deleteCommunityPet(String uid, String petKey) {
        return communityReferences.child(uid).child("list").child(petKey).removeValue();
    }






}
