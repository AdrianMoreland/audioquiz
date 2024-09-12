package com.audioquiz.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.adrian.audioquiz.databinding.FragmentWelcomeBinding;
import com.adrian.audioquiz.presentation.events.MainCoordinatorEvent;
import com.adrian.audioquiz.presentation.events.ThemeEvents;
import com.adrian.audioquiz.presentation.navigation.MainFlowCoordinator;
import com.adrian.audioquiz.presentation.viewmodel.StartViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WelcomeFragment extends Fragment {
    private static final String TAG = "WelcomeFragment";
    @Inject
    MainFlowCoordinator mainFlowCoordinator;
    private StartViewModel viewModel;
    private FragmentWelcomeBinding binding;
    private ThemeEvents themeEvents;

    public WelcomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StartViewModel.class);
        Log.d(TAG, "onCreate ");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView ");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getIsUserAuthorizedLiveData().observe(getViewLifecycleOwner(), isUserAuthorized -> binding.buttonStart.setOnClickListener(v -> {
                    Log.d(TAG, "onViewCreated, isUserAuthorized: " + isUserAuthorized);
                    handleStartButtonClick(isUserAuthorized);
                }
        ));

        binding.themeChangeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        binding.themeChangeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d(TAG, "Theme Switch button clicked " + isChecked);
            if (themeEvents != null) {
                themeEvents.toggleNightMode();
            }
        });

        Log.d(TAG, "onViewCreated ");
    }

    private void handleStartButtonClick(boolean isUserAuthorized) {
     //  MainCoordinatorEvent event1 = navigateFromAppLauncherToNotAuthorizedGraph();
//        MainCoordinatorEvent event2 = navigateFromAppLauncherToAuthorizedGraph();
        MainCoordinatorEvent event = isUserAuthorized ? navigateFromAppLauncherToAuthorizedGraph() : navigateFromAppLauncherToNotAuthorizedGraph();
        Log.d(TAG, "handleStartButtonClick " + event);
        mainFlowCoordinator.onEvent(event);
    }

    private MainCoordinatorEvent navigateFromAppLauncherToAuthorizedGraph() {
        Log.d(TAG, "navigateFromAppLauncherToAuthorizedGraph");
        return new MainCoordinatorEvent.OnContinueLoggedIn();
    }

    private MainCoordinatorEvent navigateFromAppLauncherToNotAuthorizedGraph() {
        Log.d(TAG, "navigateFromAppLauncherToNotAuthorizedGraph");
        return new MainCoordinatorEvent.OnContinueNotLoggedIn();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView ");
        binding = null;
    }
}