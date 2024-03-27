package com.example.newpetapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newpetapp.R;
import com.example.newpetapp.models.Advices;

import org.w3c.dom.Text;

import java.util.List;

public class AdvicesAdapter extends RecyclerView.Adapter<AdvicesAdapter.ViewHolder> {
    Context context;
    List<Advices>list;

    public AdvicesAdapter(Context context, List<Advices> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_view_advice,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Advices advices=list.get(position);
        holder.tvName.setText(advices.getTitle());
        holder.tvDetail.setText(advices.getDetails());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvDetail=itemView.findViewById(R.id.tvDetail);
        }
    }
}
