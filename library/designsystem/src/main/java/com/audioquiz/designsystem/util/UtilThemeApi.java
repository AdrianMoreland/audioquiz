package com.audioquiz.designsystem.util;

public interface UtilThemeApi {
    boolean isNightModeEnabled();
    void setDay();
    void setDark();
    void setFollowSystem();
    void restore();


    enum ThemeLabels {
        DAY("Day"),
        DARK("Dark"),
        FOLLOW_SYSTEM("System default");

        private final String label;

        ThemeLabels(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
