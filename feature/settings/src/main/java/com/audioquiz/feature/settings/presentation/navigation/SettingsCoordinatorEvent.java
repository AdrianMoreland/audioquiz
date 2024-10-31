package com.audioquiz.feature.settings.presentation.navigation;

public abstract class SettingsCoordinatorEvent {
    public static class OnSuccessfulLogout extends SettingsCoordinatorEvent {}
    public static class OnDeleteAccountClicked extends SettingsCoordinatorEvent {}
}
