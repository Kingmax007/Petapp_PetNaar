package com.example.newpetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.activities.ActivityPetDetails;
import com.example.newpetapp.activities.EditPetsActivity;
import com.example.newpetapp.models.ProfilePets;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class AllCommunityAdapter extends RecyclerView.Adapter<AllCommunityAdapter.ViewHolder> {
    List<ProfilePets>list;
    Context context;

    public AllCommunityAdapter(List<ProfilePets> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_cumminity_all,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null && position < list.size()) {
            ProfilePets pets = list.get(position);
            if (pets != null) {
                Picasso.get().load(pets.getImage()).into(holder.imageView);
                holder.imageView.setOnClickListener(view -> {
                    Intent intent = new Intent(view.getContext(), ActivityPetDetails.class);
                    intent.putExtra("petsList", (Serializable) list);
                    intent.putExtra("position", (Serializable) position);
                    view.getContext().startActivity(intent);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
