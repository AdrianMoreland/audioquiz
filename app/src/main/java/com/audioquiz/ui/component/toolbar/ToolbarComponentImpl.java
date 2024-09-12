package com.audioquiz.ui.component.toolbar;


import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class ToolbarComponentImpl implements ToolbarComponent {

    @Inject
    ToolbarConfiguration toolbarConfiguration;

    @Inject
    public ToolbarComponentImpl() {}

    private TextView titleView;
    private Toolbar toolbar;

    @Override
    public void initializeToolbar(Toolbar toolbar, TextView titleView) {
        this.toolbar = toolbar;
        this.titleView = titleView;
        if (toolbar != null) {
            AppCompatActivity activity = (AppCompatActivity) toolbar.getContext();
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void updateToolbar(ToolbarConfiguration configuration) {
        if (toolbar == null || titleView == null) {
            return;
        }
        if (configuration.isVisible() && toolbar != null) {
            toolbar.setVisibility(View.VISIBLE);
            if (!configuration.getTitle().isEmpty()) {
                titleView.setText(configuration.getTitle());
            }
        } else {
            if (toolbar != null) {
                toolbar.setVisibility(View.GONE); // Hide the toolbar
            }
        }
    }
}