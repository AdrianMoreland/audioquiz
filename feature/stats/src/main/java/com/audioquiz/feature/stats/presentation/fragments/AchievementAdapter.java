package com.audioquiz.feature.stats.presentation.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.designsystem.model.AchievementUi;
import com.audioquiz.feature.stats.R;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private final List<AchievementUi> achievements;

    public AchievementAdapter(List<AchievementUi> achievements) {
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_entry, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        AchievementUi achievement = achievements.get(position);
        holder.achievement_name.setText(achievement.getName());
        holder.achievement_description.setText(achievement.getDescription());
        holder.achievement_icon.setImageResource(achievement.getIcon());
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        ImageView achievement_icon;
        TextView achievement_name;
        TextView achievement_description;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            achievement_icon = itemView.findViewById(R.id.achievement_icon);
            achievement_name = itemView.findViewById(R.id.achievement_name);
            achievement_description = itemView.findViewById(R.id.achievement_description);
        }
    }
}