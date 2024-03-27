package com.example.newpetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.CommunityModel;
import com.example.newpetapp.models.PetPlaces;
import com.example.newpetapp.models.ProfilePets;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {
    List<CommunityModel>communityModelList;
    Context context;
    AllCommunityAdapter adapter;

    public CommunityAdapter(List<CommunityModel> communityModelList, Context context) {
        this.communityModelList = communityModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_community,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityModel communityModel = communityModelList.get(position);
        holder.tvUserName.setText(communityModel.getPostUserName());
        Picasso.get().load(communityModel.getImage()).into(holder.ivImage);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        adapter = new AllCommunityAdapter(communityModel.getList(),context);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return communityModelList.size();
    }

    public void filterList(List<CommunityModel> filteredList) {
        communityModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvUserName;

        ImageView ivImage;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.userName);
            ivImage=itemView.findViewById(R.id.ivImage);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
