package com.audioquiz.feature.home.navigation;

import android.os.Bundle;

public abstract class HomeCoordinatorEvent {
    public static class OnBackButtonPressed extends HomeCoordinatorEvent {}
    public static class OnCategoryClicked extends HomeCoordinatorEvent {
        private Bundle args;

        public OnCategoryClicked(Bundle args) {
            this.args = args;
        }

        public Bundle getBundle() {
            return args;
        }
    }
    public static class OnSettingsButtonPressed extends HomeCoordinatorEvent {}
    public static class OnStartQuizClicked extends HomeCoordinatorEvent {
        private Bundle args;;

        public OnStartQuizClicked(Bundle args) {
            this.args = args;
        }

        public Bundle getBundle() {
            return args;
        }
    }
}
