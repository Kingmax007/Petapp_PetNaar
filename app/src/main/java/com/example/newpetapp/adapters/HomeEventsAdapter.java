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
import com.example.newpetapp.models.Events;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeEventsAdapter extends RecyclerView.Adapter<HomeEventsAdapter.ViewHolder> {
    Context context;
    List<Events>list;

    public HomeEventsAdapter(Context context, List<Events> list) {
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
        Events events=list.get(position);
        holder.tvName.setText(events.getTitle());
        Picasso.get().load(events.getUrl()).into(holder.ivimage);
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
