package com.audioquiz.ui.component.toolbar;


import androidx.annotation.StringRes;

public class ToolbarConfiguration {
    private final boolean isVisible;
    private final String title;
    @StringRes
    private final Integer titleRes;
    private final boolean hasBackNavIcon;
    private final boolean hasLogo;

    private ToolbarConfiguration(Builder builder) {
        this.isVisible = builder.isVisible;
        this.title = builder.title;
        this.titleRes = builder.titleRes;
        this.hasBackNavIcon = builder.hasBackNavIcon;
        this.hasLogo = builder.hasLogo;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTitleRes() {
        return titleRes;
    }

    public boolean hasBackNavIcon() {
        return hasBackNavIcon;
    }

    public static class Builder {
        private boolean isVisible = true;
        private String title = "";
        @StringRes
        private Integer titleRes = null;
        private boolean hasBackNavIcon = true;
        private boolean hasLogo = false;

        public Builder setVisible(boolean isVisible) {
            this.isVisible = isVisible;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleRes(@StringRes Integer titleRes) {
            this.titleRes = titleRes;
            return this;
        }

        public Builder setHasBackNavIcon(boolean hasBackNavIcon) {
            this.hasBackNavIcon = hasBackNavIcon;
            return this;
        }

        public Builder setHasLogo(boolean hasLogo) {
            this.hasLogo = hasLogo;
            return this;
        }

        public ToolbarConfiguration build() {
            return new ToolbarConfiguration(this);
        }
    }
}