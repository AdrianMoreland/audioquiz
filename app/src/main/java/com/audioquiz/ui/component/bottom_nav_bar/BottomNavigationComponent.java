package com.audioquiz.ui.component.bottom_nav_bar;

import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;

import com.audioquiz.presentation.navigation.GlobalNavigation;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import timber.log.Timber;


@ActivityScoped
public class BottomNavigationComponent implements BottomNavigationComponentApi {
     private final Map<Integer, Runnable> buttonActions = new HashMap<>();

    @Inject
    GlobalNavigation navigation;

    @Inject
    public BottomNavigationComponent() {
        Timber.tag("BottomNavigationComponent").d("BottomNavigationComponent called");
    }

    @Override
    public void setupBottomNavigation(LinearLayout bottomNavbar) {
        initButtonActions();
        if (bottomNavbar != null) {
            for (int buttonId : buttonActions.keySet()) {
                bottomNavbar.findViewById(buttonId).setOnClickListener(v -> setupNavButton(buttonActions.get(buttonId), (Button) v));
            }
        }
    }

    public void initButtonActions() {
        buttonActions.put(com.audioquiz.designsystem.R.id.btn_learn, () -> navigation.navigateToHome());

        buttonActions.put(com.audioquiz.designsystem.R.id.btn_stats, () -> navigation.navigateToStats());

        buttonActions.put(com.audioquiz.designsystem.R.id.btn_rank, () -> navigation.navigateToRank());
    }

    private void setupNavButton(Runnable action, Button button) {
        if (action != null) { // Added null check
            button.setOnClickListener(v -> {
                button.setEnabled(false);
                action.run();
                new Handler().postDelayed(() -> button.setEnabled(true), 1000);
            });
        } else {
            throw new IllegalStateException("Action is null");
        }
    }
}
