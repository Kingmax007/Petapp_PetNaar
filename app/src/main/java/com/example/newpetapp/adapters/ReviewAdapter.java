package com.example.newpetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.ReviewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<ReviewsModel> reviewsModelList;
    Context context;

    public ReviewAdapter(List<ReviewsModel> reviewsModelList, Context context) {
        this.reviewsModelList = reviewsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_reviews, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.comments.setText(reviewsModelList.get(position).getComments());
        Picasso.get().load(reviewsModelList.get(position).getImage()).into(holder.imageView);
        holder.tvName.setText(reviewsModelList.get(position).getName());
        holder.ratingBar.setRating(reviewsModelList.get(position).getRatings());
    }

    @Override
    public int getItemCount() {
        return reviewsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, comments;

        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivProfileImage);
            comments = itemView.findViewById(R.id.tvComments);
            tvName=itemView.findViewById(R.id.tvName);
            ratingBar=itemView.findViewById(R.id.rating);
        }
    }
}
