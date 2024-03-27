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
import com.example.newpetapp.models.Events;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Context context;
    List<Events>list;

    public EventsAdapter(Context context, List<Events> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_view_events,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events events=list.get(position);
        holder.tvName.setText(events.getTitle());
        holder.date.setText(events.getDate());
        Picasso.get().load(events.getUrl()).into(holder.ivimage);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        holder.postdate.setText(currentDate);
        holder.tvAddress.setText(events.getLocation());
        holder.tvText2.setText(events.getDetails());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView date;
       ImageView ivimage;
        TextView postdate;
        TextView tvAddress;
        TextView tvText2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivimage=itemView.findViewById(R.id.ivimage);
            tvName=itemView.findViewById(R.id.tvName);
            date=itemView.findViewById(R.id.date);
            postdate=itemView.findViewById(R.id.postdate);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvText2=itemView.findViewById(R.id.tvText2);
        }
    }
}
