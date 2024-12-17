package com.audioquiz.designsystem.model;

public class ToolbarData {
    public boolean isVisible;
    public String title;
    // Add other properties as needed (e.g., hasBackNavIcon)

    public ToolbarData(boolean isVisible, String title) {
        this.isVisible = isVisible;
        this.title = title;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getTitle() {
        return title;
    }

}