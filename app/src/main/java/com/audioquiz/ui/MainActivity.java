//MainActivity.java
package com.audioquiz.ui;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.splashscreen.SplashScreenViewProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.audioquiz.R;
import com.audioquiz.databinding.ActivityMainBinding;
import com.audioquiz.presentation.events.ThemeEvents;
import com.audioquiz.presentation.navigation.NavControllerProvider;
import com.audioquiz.ui.component.bottom_nav_bar.BottomNavigationComponentApi;
import com.audioquiz.ui.component.toolbar.ToolbarComponent;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import timber.log.Timber;


/**
 * MainActivity is the entry point of the application.
 * It hosts the navigation controller and handles the theme switching.
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavControllerProvider, ThemeEvents {
    private static final String TAG = "MainActivity";

    ActivityMainBinding binding;

    private MainViewModel mainViewModel;

    private NavHostFragment navHostFragment;
    private NavController navController;

    @Inject
    ToolbarComponent toolbarComponent;
    @Inject
    BottomNavigationComponentApi bottomNavigationComponent;

    @Inject
    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setTheme(com.audioquiz.designsystem.R.style.Theme_AudioQuiz);

        // VIEW BINDING
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Access ViewModel via ViewModelProvider
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Initialize NavHostFragment and NavController
        initializeNavComponents();

        // Setup other components if NavController is available
        mainViewModel.getNavControllerLiveData().observe(this, navController2 -> {
            if (navController2 != null) {
                Timber.tag(TAG).d("NavController initialized successfully");
                splashScreen.setOnExitAnimationListener(SplashScreenViewProvider::remove);
                setupBottomNavigation();
                setupToolbar();
                applySavedTheme();
            } else {
                showErrorAndLog("NavController not initialized. Cannot proceed.");
            }
        });
    }

    private void initializeNavComponents() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Timber.tag(TAG).d("NavController initialized successfully");
            mainViewModel.setNavController(navController);
            setupOnDestinationChangedListener(navController);
        } else {
            showErrorAndLog("NavHostFragment not found");
        }
    }

    @Override
    public NavController getNavController() {
        if (navController != null) {
            Timber.tag(TAG).d("getNavController: %s", Objects.requireNonNull(navController.getCurrentDestination()).getLabel());
            return navController;
        } else {
            Timber.e("NavController is null");
            return null;
        }
    }

    private void setupToolbar() {
        // COMMENTED OUT FOR NOW FOR TESTING
        if (toolbarComponent != null) {
            Timber.tag(TAG).d("setupToolbar: ToolbarComponent initialized");
         //   toolbarComponent.initializeToolbar(binding.toolbar, findViewById(R.id.tvToolbarTitle));
        } else {
            Timber.e("ToolbarComponent is null");
        }
    }


    private void setupOnDestinationChangedListener(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Timber.tag(TAG).d("setupOnDestinationChangedListener: %s", destination.getLabel());
            // COMMENTED OUT FOR NOW FOR TESTING
/*            toolbarComponent.updateToolbar(new ToolbarConfiguration.Builder()
                    .setVisible(true)
                    .setTitle(destination.getLabel().toString())
                    .build());*/
        });
    }


    private void setupBottomNavigation() {
        if (bottomNavigationComponent != null) {
            bottomNavigationComponent.setupBottomNavigation(binding.navView.bottomNavigationContainer);
            setupBlurView();
        } else {
            Timber.e("BottomNavigationComponent is null");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        } else {
            Timber.e("NavController is null. Cannot navigate up.");
            return super.onSupportNavigateUp();
        }
    }

    private void setupBlurView() {
        float radius = 10f;
        Drawable windowBackground = getWindow().getDecorView().getBackground();
        BlurView blurView = binding.blurView;
        blurView.setupWith(findViewById(android.R.id.content), new RenderScriptBlur(this)).setFrameClearDrawable(windowBackground).setBlurRadius(radius);
        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);
    }

    private void showErrorAndLog(String message) {
        Toast.makeText(this, "An error occurred. Please restart the app.", Toast.LENGTH_LONG).show();
        Timber.e(message);
    }

    // ------------------Theme Management
    private void applySavedTheme() {
        new Handler().post(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("default_preferences", MODE_PRIVATE);
            String theme = sharedPreferences.getString("theme", "day");
            int nightMode = theme.equals("night") ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(nightMode);
        });
    }

    public void toggleNightMode() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        int newMode = (currentMode == AppCompatDelegate.MODE_NIGHT_YES) ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(newMode);

        updateStatusBarAndUi(newMode == AppCompatDelegate.MODE_NIGHT_YES);
        saveThemeChoice(newMode == AppCompatDelegate.MODE_NIGHT_YES ? "night" : "day");
    }

    private void updateStatusBarAndUi(boolean isNightMode) {
        Window window = getWindow();
        int statusBarColor = isNightMode ? com.audioquiz.designsystem.R.color.dark_status_bar_color : com.audioquiz.designsystem.R.color.light_status_bar_color;
        int uiVisibility = isNightMode ? (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) : 0;
        window.setStatusBarColor(ContextCompat.getColor(this, statusBarColor));
        window.getDecorView().setSystemUiVisibility(uiVisibility);
    }

    private void saveThemeChoice(String theme) {
        SharedPreferences sharedPreferences = getSharedPreferences("default_preferences", MODE_PRIVATE);
        sharedPreferences.edit().putString("theme", theme).apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear the binding reference to avoid memory leaks
        binding = null;
    }

}