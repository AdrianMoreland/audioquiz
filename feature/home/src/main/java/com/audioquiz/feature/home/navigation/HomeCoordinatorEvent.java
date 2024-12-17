package com.audioquiz.feature.home.navigation;

public abstract class HomeCoordinatorEvent {
    public static class OnBackButtonPressed extends HomeCoordinatorEvent {}
    public static class OnCategoryClicked extends HomeCoordinatorEvent {}
    public static class OnSettingsButtonPressed extends HomeCoordinatorEvent {}
}
