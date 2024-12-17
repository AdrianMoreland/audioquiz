package com.audioquiz.feature.login.domain;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.audioquiz.designsystem.base.view.ViewEvent;
import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewState;

public class LoginViewContract {

    private LoginViewContract() {
        // Private constructor to prevent instantiation
    }

    /**
     * ViewState:
     * Part of the model layer, the view observes this model for state changes.
     * Represents the current state of the view at any given time.
     * So this class should have all the variable content on which the view is dependent.
     * Every time there is any user input/action we will expose modified copy (to maintain the previous state which is not being modified) of this class.
     */
    public static class State implements ViewState {
        private final boolean isLoading;
        private final String email;
        private final String password;
        private final boolean isDataValid;
        @Nullable
        private final Integer usernameError;
        @Nullable
        private final Integer passwordError;

        public State(boolean isLoading, String email, String password, boolean isDataValid) {
            this.isLoading = isLoading;
            this.email = email;
            this.password = password;
            this.isDataValid = isDataValid;
            this.usernameError = null;
            this.passwordError = null;
        }
        public State(boolean isLoading, String email, String password, @Nullable Integer usernameError, @Nullable Integer passwordError) {
            this.isLoading = isLoading;
            this.email = email;
            this.password = password;
            this.isDataValid = false;
            this.usernameError = usernameError;
            this.passwordError = passwordError;
        }

        public boolean isLoading() {
            return isLoading;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
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

        public static class OnLoginButtonClicked extends Event {
        }
        public static class OnGoogleSignInSuccess extends Event {
            private final String idToken;

            public OnGoogleSignInSuccess(String idToken) {
                this.idToken = idToken;
            }

            public String getIdToken() {
                return idToken;
            }
        }
        public static class OnForgotPasswordLinkClicked extends Event {        }
        public static class OnSignupLinkClicked extends Event {}
        public static class OnUserNameTextChanged extends Event {
            private final String username;

            public OnUserNameTextChanged(String username) {
                this.username = username;
            }

            public String getUsername() {
                return username;
            }
        }
        public static class OnGoogleSignInError extends Event {
            private final Throwable throwable;

            public OnGoogleSignInError(Throwable throwable) {
                this.throwable = throwable;
            }

            public Throwable getThrowable() {
                return throwable;
            }
        }
        public static class OnPasswordTextChanged extends Event {
            private final String password;

            public OnPasswordTextChanged(String password) {
                this.password = password;
            }

            public String getPassword() {
                return password;
            }
        }

        public static class OnGoogleButtonClicked extends Event {
            private FragmentActivity activity;

            public OnGoogleButtonClicked(FragmentActivity activity) {
                this.activity = activity;
            }

            public FragmentActivity getActivity() {
                return activity;
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

        public static class ShowSuccessToast extends Effect {
            private final String message;

            public ShowSuccessToast(String message) {
                this.message = message;
            }

            public String getMessage() {
                return message;
            }
        }
    }

}