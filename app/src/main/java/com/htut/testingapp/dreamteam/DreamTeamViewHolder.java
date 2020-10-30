package com.htut.testingapp.dreamteam;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htut.testingapp.R;
public class DreamTeamViewHolder extends RecyclerView.ViewHolder {
    DreamItemAdapter dreamItemAdapter;
    RecyclerView rv;

    public DreamTeamViewHolder(@NonNull View itemView) {
        super(itemView);
        rv = itemView.findViewById(R.id.rv);
        settingAdapter();
    }

    void bind(LineUpRow data){
        dreamItemAdapter.setDataset(data.getPlayers());
    }
    private void settingAdapter() {
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(
                new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        dreamItemAdapter = new DreamItemAdapter();
        rv.setAdapter(dreamItemAdapter);
    }
}
