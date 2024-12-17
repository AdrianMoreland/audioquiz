package com.audioquiz.feature.login.domain;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    LoginResult() {
    }

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }


    public static class Success extends LoginResult {
        private final LoggedInUserView user;

        public Success(LoggedInUserView user) {
            this.user = user;
        }

        public LoggedInUserView getUser() {
            return user;
        }
    }

    public static class Error extends LoginResult {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}