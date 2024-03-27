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
import com.example.newpetapp.models.PetPlaces;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PetPlacesAdapter extends RecyclerView.Adapter<PetPlacesAdapter.ViewHolder> {
    Context context;
    List<PetPlaces>list;

    public PetPlacesAdapter(Context context, List<PetPlaces> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_view_pet_friendly_places,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetPlaces places=list.get(position);
        holder.tvName.setText(places.getName());
        holder.tvAddress.setText(places.getLocation());
        Picasso.get().load(places.getUrl()).into(holder.ivImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<PetPlaces> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvAddress;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvAddress=itemView.findViewById(R.id.address);
            ivImage=itemView.findViewById(R.id.ivimage);
        }
    }
}
