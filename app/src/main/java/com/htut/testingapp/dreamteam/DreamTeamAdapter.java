package com.htut.testingapp.dreamteam;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htut.testingapp.R;

import java.util.ArrayList;
import java.util.List;

public class DreamTeamAdapter extends RecyclerView.Adapter<DreamTeamViewHolder> {
    List<LineUpRow> dataset = new ArrayList<>();
    String path;

    @NonNull
    @Override
    public DreamTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DreamTeamViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.dream_team_recycler, parent, false));

    }
    public void setDataset(List<LineUpRow> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
    public void setImageBitmap(String path){
        this.path = path;
        notifyDataSetChanged();
    }

    public LineUpRow getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull DreamTeamViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
