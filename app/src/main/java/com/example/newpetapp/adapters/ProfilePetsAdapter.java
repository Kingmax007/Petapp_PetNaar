package com.example.newpetapp.adapters;

import static androidx.core.content.ContextCompat.getDrawable;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityAddPet;
import com.example.newpetapp.activities.EditPetsActivity;
import com.example.newpetapp.models.ProfilePets;
import com.example.newpetapp.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfilePetsAdapter extends RecyclerView.Adapter<ProfilePetsAdapter.ViewHolder> {
    Context context;
    List<ProfilePets> list;

    DatabaseHelper db;

    public ProfilePetsAdapter(Context context, List<ProfilePets> list) {
        this.context = context;
        this.list = list;
        db = new DatabaseHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_profile, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfilePets profilePets = list.get(position);
        Picasso.get().load(profilePets.getImage()).into(holder.ivImage);
        holder.tvName.setText(profilePets.getName());
        holder.tvAge.setText(profilePets.getAge());
        holder.tvType.setText(profilePets.getType());
        holder.tvbreed.setText(profilePets.getBreed());
        holder.tvInterest.setText(profilePets.getInterest());
        holder.ivMenu.setImageResource(R.drawable.ic_menu2);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivImage;
        TextView tvName;
        TextView tvAge, tvType, tvbreed, tvInterest;

        ImageView ivMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvType = itemView.findViewById(R.id.tvType);
            tvbreed = itemView.findViewById(R.id.tvbreed);
            tvInterest = itemView.findViewById(R.id.tvInterest);
            ivMenu = itemView.findViewById(R.id.menu);

            // Set click listener for the menu icon
            ivMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Handle click events
            if (v.getId() == R.id.menu) {
                // Show the dropdown menu
                showPopupMenu();
            }
        }

        private void showPopupMenu() {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), ivMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_profile_pets, popupMenu.getMenu());

            // Set menu item click listener
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit_option:
                            item.setIcon(ContextCompat.getDrawable(itemView.getContext(), R.drawable.icon_edit));
                            ProfilePets pet = list.get(getAdapterPosition());
                            Intent intent = new Intent(itemView.getContext(), EditPetsActivity.class);
                            intent.putExtra("pets", pet);
                            itemView.getContext().startActivity(intent);
                            return true;
                        case R.id.delete_option:
                            String petKey = list.get(getAdapterPosition()).getKey();
                            deletePet(petKey);
                            notifyDataSetChanged();
                            item.setIcon(ContextCompat.getDrawable(itemView.getContext(), R.drawable.icon_delete));
                            return true;
                        default:
                            return false;
                    }
                }
            });

            // Show the popup menu
            popupMenu.show();
        }
    }

    private void deletePet(String petKey) {
        // Get the current user's UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Delete the pet from My Pets
        db.deleteMyPet(uid, petKey)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Fetch the updated list of pets
                        db.getMyPets(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<ProfilePets> updatedPetsList = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    ProfilePets pet = dataSnapshot.getValue(ProfilePets.class);
                                    updatedPetsList.add(pet);
                                }
                                // Update the pets list in the Community database
                                db.updateCommunityPetsList(uid, updatedPetsList)
                                        .addOnCompleteListener(communityTask -> {
                                            if (communityTask.isSuccessful()) {
                                                // Pet deleted successfully from both My Pets and Community
                                                Toast.makeText(context, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Handle the case when updating Community fails
                                                Toast.makeText(context, "Failed to update Community", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle onCancelled event
                            }
                        });
                    } else {
                        // Handle the case when deleting from My Pets fails
                        Toast.makeText(context, "Failed to delete pet", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
