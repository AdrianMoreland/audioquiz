package com.audioquiz.feature.rank;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.designsystem.R;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Random;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<RankEntry> rankEntries;
    private final Random  random = new Random();
    private final boolean useWeeklyScore;

    public RankAdapter(List<RankEntry> rankEntries, boolean useWeeklyScore) {
        this.rankEntries = rankEntries;
        this.useWeeklyScore = useWeeklyScore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.audioquiz.feature.rank.R.layout.rank_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankEntry entry = rankEntries.get(position);
        holder.tvUsername.setText(entry.getUsername());

        if (useWeeklyScore) {
            holder.tvScore.setText(holder.itemView.getContext().getString(R.string.score_text, entry.getWeeklyScore()));
        } else {
            holder.tvScore.setText(holder.itemView.getContext().getString(R.string.score_text, entry.getTotalScore()));
        }

        // Set the appropriate medal image and text based on the user's rank
        int rank = position + 1;
        if (rank == 1) {
            holder.imageRankMedal.setImageResource(R.drawable.ic_medal_gold2);
            holder.tvRank.setText("");
        } else if (rank == 2) {
            holder.imageRankMedal.setImageResource(R.drawable.ic_medal_silver2);
            holder.tvRank.setText("");
        } else if (rank == 3) {
            holder.imageRankMedal.setImageResource(R.drawable.ic_medal_bronze2);
            holder.tvRank.setText("");
        } else {
            holder.imageRankMedal.setVisibility(View.GONE);
            holder.tvRank.setText(String.valueOf(rank));
        }

        String imageUrl = entry.getProfileImage();

        // Check if the imageUrl is null or empty
        if (imageUrl == null || imageUrl.isEmpty()) {
            Log.d("RankAdapter", "imageUrl is null or empty");
            // Set the first letter of the username as the text
            String username = entry.getUsername();
            if (username != null && !username.isEmpty()) {
                char firstLetter = Character.toUpperCase(username.charAt(0));
                holder.tvUserInitial.setText(String.valueOf(firstLetter));
            }
            holder.tvUserInitial.setVisibility(View.VISIBLE); // Show the TextView

            // Set the background color of the MaterialCardView to a random color
            holder.containerImageUserRanks.setCardBackgroundColor(getRandomColor(holder.containerImageUserRanks.getContext()));
        } else {
            // Use Glide to load the image from the URL into the ImageView
            Glide.with(holder.userImage.getContext())
                    .load(imageUrl)
                    .error(R.drawable.ic_incorrect)
                    .into(holder.userImage);
            holder.tvUserInitial.setVisibility(View.GONE); // Hide the TextView
        }
    }
    @Override
    public int getItemCount() {
        return rankEntries.size();
    }

    public void updateData(List<RankEntry> newEntries) {
        int oldSize = this.rankEntries.size();
        this.rankEntries = newEntries;
        int newSize = this.rankEntries.size();
        for (int i = oldSize; i < newSize; i++) {
            notifyItemInserted(i);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank;
        ImageView imageRankMedal;
        TextView tvUsername;
        TextView tvUserInitial;
        ImageView userImage;
        TextView tvScore;
        MaterialCardView containerImageUserRanks;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(com.audioquiz.feature.rank.R.id.text_rank_position);
            imageRankMedal = itemView.findViewById(com.audioquiz.feature.rank.R.id.image_rank_medal);
            tvUsername = itemView.findViewById(com.audioquiz.feature.rank.R.id.text_username_rank);
            tvScore = itemView.findViewById(com.audioquiz.feature.rank.R.id.text_total_score_rank);
            tvUserInitial = itemView.findViewById(com.audioquiz.feature.rank.R.id.text_user_initials_rank);
            userImage = itemView.findViewById(com.audioquiz.feature.rank.R.id.image_user_rank);
            containerImageUserRanks = itemView.findViewById(com.audioquiz.feature.rank.R.id.container_image_user_ranks);
        }
    }

    private int getRandomColor(Context context) {
        int[] colorArray = {
                context.getResources().getColor(R.color.md_theme_primary, null),
                context.getResources().getColor(R.color.md_theme_secondary, null),
                context.getResources().getColor(R.color.md_theme_tertiary, null),
                context.getResources().getColor(R.color.colorCustomColor1, null),
                context.getResources().getColor(R.color.colorCustomColor2, null),
                context.getResources().getColor(R.color.colorCustomColor3, null)
        };

        int randomIndex = random.nextInt(colorArray.length); // Use the Random instance
        return colorArray[randomIndex];
    }
}