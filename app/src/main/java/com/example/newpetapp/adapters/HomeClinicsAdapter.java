package com.example.newpetapp.adapters;

import android.content.Context;
import android.hardware.lights.LightsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.PetClinics;
import com.example.newpetapp.models.PetPlaces;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeClinicsAdapter extends RecyclerView.Adapter<HomeClinicsAdapter.ViewHolder> {
   Context context;
   List<PetClinics>list;

    public HomeClinicsAdapter(Context context, List<PetClinics> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_view_home,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetClinics clinics=list.get(position);
        holder.tvName.setText(clinics.getName());
        Picasso.get().load(clinics.getUrl()).into(holder.ivimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView ivimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivimage=itemView.findViewById(R.id.iv1);
            tvName=itemView.findViewById(R.id.tvName);
        }
    }
}
