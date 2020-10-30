package com.htut.testingapp.dreamteam;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htut.testingapp.R;

import java.util.ArrayList;
import java.util.List;

public class DreamItemAdapter extends RecyclerView.Adapter<DreamItemViewHolder> {
    List<Player> dataset = new ArrayList<>();
    String path;

    public void setDataset(List<Player> dataset) {
        this.dataset = dataset;
        this.path = path;
        notifyDataSetChanged();
    }

    public Player getItem(int position) {
        return dataset.get(position);
    }


    @NonNull
    @Override
    public DreamItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DreamItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_image_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull DreamItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
