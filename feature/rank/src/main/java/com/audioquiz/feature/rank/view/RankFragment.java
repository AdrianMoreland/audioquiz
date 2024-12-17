package com.audioquiz.feature.rank.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.designsystem.extensions.FragmentExtension;
import com.audioquiz.feature.rank.R;
import com.audioquiz.feature.rank.RankAdapter;
import com.audioquiz.feature.rank.viewmodel.RankViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class RankFragment extends Fragment {
    private static final String TAG = "RankFragment";

    private int primaryColor;
    private int transparentColor;

    private MaterialButton buttonAllTimeRank;
    private MaterialButton buttonWeeklyRank;
    private TextView textRankTitle;

    private LinearLayout containerRank;

    private LinearLayout containerWeeklyRank;
    private NestedScrollView nestedScrollView;

    @Inject
    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentExtension fragmentExtension = new FragmentExtension();

        primaryColor = FragmentExtension.getColor(this, com.audioquiz.designsystem.R.color.md_theme_primary);
        transparentColor = FragmentExtension.getColor(this, com.audioquiz.designsystem.R.color.color_transparency);


        nestedScrollView = view.findViewById(R.id.main_scroll_view);
        textRankTitle = view.findViewById(R.id.text_rank_title);
        // Initialize All Time Rank button and set click listener
        buttonAllTimeRank = view.findViewById(R.id.button_all_time_rank);
        buttonAllTimeRank.setOnClickListener(v -> showAllTimeRank());

        // Initialize Weekly Rank button and set click listener
        buttonWeeklyRank = view.findViewById(R.id.button_weekly_rank);
        buttonWeeklyRank.setOnClickListener(v -> showWeeklyRank());

        // Initialize All Time Rank RecyclerView and Adapter
        RecyclerView rankRecyclerView = view.findViewById(R.id.recyclerview_rank);
        containerRank = view.findViewById(R.id.container_rank);
        rankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RankAdapter rankAdapter = new RankAdapter(new ArrayList<>(), false); // Use total score
        rankRecyclerView.setAdapter(rankAdapter);

        // Initialize Weekly Rank RecyclerView and Adapter
        RecyclerView rankWeeklyRecyclerView = view.findViewById(R.id.recyclerview_weekly_rank);
        containerWeeklyRank = view.findViewById(R.id.container_weekly_rank);
        rankWeeklyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RankAdapter rankWeeklyAdapter = new RankAdapter(new ArrayList<>(), true); // Use weekly score
        rankWeeklyRecyclerView.setAdapter(rankWeeklyAdapter);

        // Initialize your ViewModel with the factory
        RankViewModel viewModel = new ViewModelProvider(this).get(RankViewModel.class);


        // Fetch the rank entries
        List<RankEntry> rankEntries = viewModel.getRankEntries();
        Timber.tag(TAG).d("All time rank entries fetched: %s", rankEntries.size());
        rankAdapter.updateData(rankEntries);

        // Fetch the weekly rank entries
        List<RankEntry> rankWeeklyEntries = viewModel.getRankWeeklyEntries();
        Timber.tag(TAG).d("Weekly rank entries fetched: %s", rankWeeklyEntries.size());
        rankWeeklyAdapter.updateData(rankWeeklyEntries);

        // Show the all-time rank by default
        showAllTimeRank();
    }

    private void showAllTimeRank() {
        // Change button styles
        buttonAllTimeRank.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
        buttonWeeklyRank.setBackgroundTintList(ColorStateList.valueOf(transparentColor));

        textRankTitle.setText("Top 30 Players");

        // Show the all-time rank and hide the weekly rank
        containerRank.setVisibility(View.VISIBLE);
        containerWeeklyRank.setVisibility(View.GONE);

        // Post a runnable to the view's handler to scroll to the top of the NestedScrollView
        nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, 0));
    }

    private void showWeeklyRank() {
        // Change button styles
        buttonWeeklyRank.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
        buttonAllTimeRank.setBackgroundTintList(ColorStateList.valueOf(transparentColor));

        textRankTitle.setText("Top 10 Players this week");

        // Show the weekly rank and hide the all-time rank
        containerRank.setVisibility(View.GONE);
        containerWeeklyRank.setVisibility(View.VISIBLE);

        // Post a runnable to the view's handler to scroll to the top of the NestedScrollView
        nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, 0));
    }


}