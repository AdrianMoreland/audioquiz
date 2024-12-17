package com.audioquiz.feature.login.presentation.navigation;

public abstract class LoginCoordinatorEvent  {
    public static class OnLoginSuccess extends LoginCoordinatorEvent {}
    public static class ForgotPinCode extends LoginCoordinatorEvent {}
    public static class BackToLogin extends LoginCoordinatorEvent {}
    public static class OnSignupClicked extends LoginCoordinatorEvent {}
}
