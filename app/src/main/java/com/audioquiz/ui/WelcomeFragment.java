package com.audioquiz.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audioquiz.databinding.FragmentWelcomeBinding;
import com.audioquiz.presentation.events.MainCoordinatorEvent;
import com.audioquiz.presentation.events.ThemeEvents;
import com.audioquiz.presentation.navigation.MainFlowCoordinator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class WelcomeFragment extends Fragment {
    private static final String TAG = "WelcomeFragment";

    private StartViewModel viewModel;
    private FragmentWelcomeBinding binding;
    private ThemeEvents themeEvents;

    @Inject
    MainFlowCoordinator mainFlowCoordinator;

    public WelcomeFragment() {
        Timber.tag(TAG).d("WelcomeFragment ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StartViewModel.class);
        Timber.d("onCreate ");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        Timber.tag(TAG).d("onCreateView ");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getIsUserAuthorizedLiveData().observe(getViewLifecycleOwner(), isUserAuthorized -> binding.buttonStart.setOnClickListener(v -> {
            Timber.tag(TAG).d("onViewCreated, isUserAuthorized: %s", isUserAuthorized);
                    handleStartButtonClick(isUserAuthorized);
                }
        ));

        binding.themeChangeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        binding.themeChangeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Timber.tag(TAG).d("Theme Switch button clicked %s", isChecked);
            if (themeEvents != null) {
                themeEvents.toggleNightMode();
            }
        });

        Timber.tag(TAG).d("onViewCreated ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ThemeEvents themeevents) {
            this.themeEvents = themeevents;
        } else {
            throw new ClassCastException(context + " must implement ThemeEvents");
        }
    }


    private void handleStartButtonClick(boolean isUserAuthorized) {
        MainCoordinatorEvent event = isUserAuthorized ? navigateFromAppLauncherToAuthorizedGraph() : navigateFromAppLauncherToNotAuthorizedGraph();
        Timber.tag(TAG).d("handleStartButtonClick %s", event);
        mainFlowCoordinator.onEvent(event);
    }

    private MainCoordinatorEvent navigateFromAppLauncherToAuthorizedGraph() {
        Timber.tag(TAG).d("navigateFromAppLauncherToAuthorizedGraph");
        return new MainCoordinatorEvent.OnContinueLoggedIn();
    }

    private MainCoordinatorEvent navigateFromAppLauncherToNotAuthorizedGraph() {
        Timber.tag(TAG).d("navigateFromAppLauncherToNotAuthorizedGraph");
        return new MainCoordinatorEvent.OnContinueNotLoggedIn();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.tag(TAG).d("onDestroyView ");
        binding = null;
    }
}