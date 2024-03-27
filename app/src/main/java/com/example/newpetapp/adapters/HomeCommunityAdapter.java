package com.example.newpetapp.adapters;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.CommunityModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCommunityAdapter extends RecyclerView.Adapter<HomeCommunityAdapter.ViewHolder> {
  Context context;
  List<CommunityModel> list;

    public HomeCommunityAdapter(Context context, List<CommunityModel> list) {
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
        CommunityModel communityModel=list.get(position);
        holder.tvName.setText(communityModel.getPostUserName());
        Picasso.get().load(communityModel.getImage()).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage=itemView.findViewById(R.id.iv1);
            tvName=itemView.findViewById(R.id.tvName);
        }
    }


}
