package com.example.newpetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.ProfilePets;

import org.w3c.dom.Text;

import java.util.List;

public class ProfilePetsAdapter extends RecyclerView.Adapter<ProfilePetsAdapter.ViewHolder> {
   Context context;
   List<ProfilePets>list;

    public ProfilePetsAdapter(Context context, List<ProfilePets> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.row_view_profile,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfilePets profilePets=list.get(position);
        holder.ivImage.setImageResource(profilePets.getImage());
        holder.tvName.setText(profilePets.getName());
        holder.tvDetails.setText(profilePets.getDetails());
        holder.ivMenu.setImageResource(profilePets.getImage2());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvDetails;

        ImageView ivMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage=itemView.findViewById(R.id.ivImage);
            tvName=itemView.findViewById(R.id.tvName);
            tvDetails=itemView.findViewById(R.id.tvDetail);
            ivMenu=itemView.findViewById(R.id.menu);
        }
    }
}
