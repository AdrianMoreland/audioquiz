package com.audioquiz.core.model.util;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public abstract class Result<T> {
    // OneTapSignInResponse represents a Response<BeginSignInResult>
// FirebaseSignInResponse represents a Response<AuthResult>
// SignOutResponse represents a Response<Boolean>
// DeleteAccountResponse represents a Response<Boolean>
// AuthStateResponse represents a LiveData<FirebaseUser>

    private Result() {
    }

    @Override
    public String toString() {
        if (this instanceof Result.Success<T> success) {
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error error) {
            return "Error[exception=" + error.getException().toString() + "]";
        }
        return "";
    }

    public static final class Loading extends Result<Void> {
        public static final Loading INSTANCE = new Loading();

        private Loading() {
        }
    }

    // Success sub-class
    public static final class Success<T> extends Result<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public static final class Error extends Result<Void> {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}