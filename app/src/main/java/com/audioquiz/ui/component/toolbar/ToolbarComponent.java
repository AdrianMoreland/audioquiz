package com.audioquiz.ui.component.toolbar;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public interface ToolbarComponent {
    void initializeToolbar(Toolbar toolbar, TextView titleView);

    void updateToolbar(ToolbarConfiguration configuration);
}
