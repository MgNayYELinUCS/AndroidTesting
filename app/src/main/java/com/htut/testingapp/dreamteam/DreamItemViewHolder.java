package com.htut.testingapp.dreamteam;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htut.testingapp.R;

public class DreamItemViewHolder extends RecyclerView.ViewHolder {
    TextView rv_dream_team_name;
    ImageView iv_jersey;
    public DreamItemViewHolder(@NonNull View itemView) {
        super(itemView);
        rv_dream_team_name = itemView.findViewById(R.id.rv_dream_team_name);
        iv_jersey = itemView.findViewById(R.id.iv_jersey);
    }

    void bind(Player data){
        rv_dream_team_name.setText(data.getName());

    }
}
