package com.audioquiz.ui.component.bottom_nav_bar;

import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.adrian.audioquiz.presentation.navigation.GlobalNavigation;
import com.adrian.home.R;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;


@ActivityScoped
public class BottomNavigationComponent implements BottomNavigationComponentApi {
     private final Map<Integer, Runnable> buttonActions = new HashMap<>();

    @Inject
    GlobalNavigation navigation;

    @Inject
    public BottomNavigationComponent() {
         Log.d("BottomNavigationComponent", "BottomNavigationComponent called");
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
        buttonActions.put(com.adrian.ui.R.id.btn_learn, () -> navigation.navigateToHome());

        buttonActions.put(com.adrian.ui.R.id.btn_stats, () -> navigation.navigateToStats());

        buttonActions.put(com.adrian.ui.R.id.btn_rank, () -> navigation.navigateToRank());
    }

    private void setupNavButton(Runnable action, Button button) {
       // Button button = activity.findViewById(buttonId);
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
