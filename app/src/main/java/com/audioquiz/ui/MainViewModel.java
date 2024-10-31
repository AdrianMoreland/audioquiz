package com.audioquiz.ui;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.audioquiz.presentation.events.Event;
import com.audioquiz.ui.component.toolbar.ToolbarConfiguration;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isNightMode = new MutableLiveData<>(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES);
    private final MutableLiveData<ToolbarConfiguration> toolbarConfiguration = new MutableLiveData<>();
    private final MutableLiveData<Boolean> bottomNavigationVisibility = new MutableLiveData<>(true); // Initially visible
    private final MutableLiveData<Event<String>> errorMessage = new MutableLiveData<>();

    private final MutableLiveData<NavController> navControllerLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel() {
        // Required empty public constructor
    }

    public LiveData<NavController> getNavControllerLiveData() {
        return navControllerLiveData;
    }

    public void setNavController(NavController navController) {
        navControllerLiveData.setValue(navController);
    }

    public LiveData<Boolean> isNightMode() {
        return isNightMode;
    }

    public LiveData<ToolbarConfiguration> getToolbarConfiguration() {
        return toolbarConfiguration;
    }

    public LiveData<Boolean> getBottomNavigationVisibility() {
        return bottomNavigationVisibility;
    }

    public LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    public void toggleNightMode() {
        boolean currentMode = Boolean.TRUE.equals(isNightMode.getValue());
        isNightMode.setValue(!currentMode);
    }

    public void updateToolbarConfiguration(ToolbarConfiguration config) {
        toolbarConfiguration.setValue(config);
    }

    public void showBottomNavigation(boolean isVisible) {
        bottomNavigationVisibility.setValue(isVisible);
    }

    public void showErrorMessage(String message) {
        errorMessage.setValue(new Event<>(message));
    }

}
