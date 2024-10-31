package com.audioquiz.feature.settings.domain;

import androidx.annotation.Nullable;

import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewEvent;
import com.audioquiz.designsystem.base.view.ViewState;

public class SettingsViewContract {

    /**
     * ViewState:
     * Part of the model layer, the view observes this model for state changes.
     * Represents the current state of the view at any given time.
     * So this class should have all the variable content on which the view is dependent.
     * Every time there is any user input/action we will expose modified copy (to maintain the previous state which is not being modified) of this class.
     */
    public static class State implements ViewState {
        private final boolean isLoading;
        private final String usernameLetter;
        private final String imageUri;
        private final boolean isDataValid;
        @Nullable
        private final Integer usernameError;
        @Nullable
        private final Integer passwordError;

        public State(boolean isLoading, String usernameLetter, String imageUri, @Nullable Integer usernameError, @Nullable Integer passwordError) {
            this.isLoading = isLoading;
            this.usernameLetter = usernameLetter;
            this.imageUri = imageUri;
            this.isDataValid = false;
            this.usernameError = usernameError;
            this.passwordError = passwordError;
        }

        public boolean isLoading() {
            return isLoading;
        }

        public String getUsername() {
            return usernameLetter;
        }

        public String getImageUri() {
            return imageUri;
        }

        public boolean isDataValid() {
            return isDataValid;
        }

        @Nullable
        public Integer getUsernameError() {
            return usernameError;
        }

        @Nullable
        public Integer getPasswordError() {
            return passwordError;
        }
    }

    /**
     * ViewEvent:
     * It represents all actions/events a user can perform on the view.
     * This is used to pass user input/action to the ViewModel.
     */
    public abstract static class Event implements ViewEvent {
        private Event() {}

        public static class OnConfirmChangesButtonClicked extends Event {}
        public static class OnChangePasswordButtonClicked extends Event {}
        public static class OnLogoutButtonClicked extends Event {        }
        public static class OnDeleteAccountButtonClicked extends Event {}
        public static class OnUserNameTextChanged extends Event {
            private final String username;

            public OnUserNameTextChanged(String username) {
                this.username = username;
            }

            public String getUsername() {
                return username;
            }
        }
        public static class OnEmailTextChanged extends Event {
            private final String email;

            public OnEmailTextChanged(String email) {
                this.email = email;
            }

            public String getPassword() {
                return email;
            }
        }
    }

    /**
     * ViewEffect:
     * In Android, we have certain actions that are more like fire-and-forget, for example- Toast, in those cases, we can not use ViewState as it maintains state.
     * It means, if we use ViewState to show a Toast, it will be shown again on configuration change or every time there
     * is a new state unless and until we reset its state by passing ‘toast is shown’ event.
     * And if you do not wish to do that, you can use ViewEffect as it is based on SingleLiveEvent and does not maintain state.
     */
    public abstract static  class Effect implements ViewEffect {
        private Effect() {}

        public static class ShowErrorToast extends Effect {
            private final String error;

            public ShowErrorToast(String error) {
                this.error = error;
            }

            public String getError() {
                return error;
            }
        }
    }


}
