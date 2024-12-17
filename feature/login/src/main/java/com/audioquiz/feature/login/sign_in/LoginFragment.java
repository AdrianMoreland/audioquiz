package com.audioquiz.feature.login.sign_in;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewState;
import com.audioquiz.designsystem.model.ToolbarData;
import com.audioquiz.feature.login.R;
import com.audioquiz.feature.login.databinding.FragmentLoginBinding;
import com.audioquiz.feature.login.domain.LoginViewContract;
import com.audioquiz.feature.login.presentation.navigation.LoginCoordinatorEvent;
import com.audioquiz.feature.login.presentation.navigation.LoginFlowCoordinator;
import com.audioquiz.library.util.StringExtensions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * LoginFragment is a Fragment subclass that provides the user interface for the login screen.
 * It handles user interactions and communicates with the LoginViewModel to perform operations.
 */
@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private NavController navController;
    String errorMessage;

    @Inject
    LoginFlowCoordinator loginFlowCoordinator;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        ToolbarData toolbarData = new ToolbarData(true, "Login");
        binding.setToolbarData(toolbarData);
        errorMessage = getString(R.string.login_failed);

        navController = findNavController(this);
        int graphId = navController.getGraph().getId();
        Timber.d("NavController graph ID: %d", graphId);

        Timber.tag(TAG).d("Login Fragment created");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
        observeViewModel();
    }

    public void initializeComponents() {
        // Set up listeners for UI elements
        binding.btnSignIn.setOnClickListener(v ->
                loginViewModel.process(new LoginViewContract.Event.OnLoginButtonClicked()));
        binding.btnLoginGoogle.setOnClickListener(v ->
                loginViewModel.process(new LoginViewContract.Event.OnGoogleButtonClicked(requireActivity())));
//        binding.btnLoginGoogle.setOnClickListener(v -> signInWithGoogle());
        binding.btnLoginToSignup.setOnClickListener(v -> {
            try {
                navController.navigate(R.id.action_loginFragment_to_signupFragment);
            } catch (IllegalArgumentException e) {
                Timber.tag(TAG).e(e, "Navigation failed");
            }
        });
        binding.forgotPassword.setOnClickListener(v ->
                onForgotPasswordClicked());
        binding.btnBackLogin.setOnClickListener(v ->
                NavHostFragment.findNavController(this).popBackStack());

        TextWatcher afterTextChangedListener = setupTextWatcher();
        binding.loginEmailEditText.addTextChangedListener(afterTextChangedListener);
        binding.loginPasswordEditText.addTextChangedListener(afterTextChangedListener);
        binding.loginPasswordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.process(new LoginViewContract.Event.OnLoginButtonClicked());
            }
            return false;
        });
    }

    protected void observeViewModel() {
        loginViewModel.getViewState().observe(getViewLifecycleOwner(), this::onViewStateChanged);
        loginViewModel.getViewEffect().observe(getViewLifecycleOwner(), this::onViewEffectReceived);
        loginViewModel.navigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);
    }

    protected void onViewStateChanged(ViewState viewState) {
        if (viewState instanceof LoginViewContract.State state) {
            if (state.isLoading()) {
                binding.loading.setVisibility(View.VISIBLE);
                binding.grpInput.setVisibility(View.GONE);
            } else {
                binding.loading.setVisibility(View.GONE);
                binding.grpInput.setVisibility(View.VISIBLE);
                binding.btnLoginToSignup.setEnabled(!state.isLoading());
            }
            binding.btnLoginGoogle.setEnabled(!state.isLoading());
            binding.btnSignIn.setEnabled(!state.isLoading());
        }
    }

    protected void onViewEffectReceived(ViewEffect viewEffect) {
        if (viewEffect instanceof LoginViewContract.Effect.ShowErrorToast showErrorToast) {
            Toast.makeText(getContext(), showErrorToast.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNavigation(LoginCoordinatorEvent event) {
        if (loginFlowCoordinator != null) {
            loginFlowCoordinator.onEvent(event);
        } else {
            Timber.tag("LoginFragment").e("LoginFlowCoordinator is null");
        }
    }


/*

    private void signInWithGoogle() {
        Timber.tag(TAG).d("signInWithGoogle");
        binding.loading.setVisibility(View.VISIBLE);
        try {
            googleSignInHelper.signIn(requireActivity(), new GoogleSignInHelper.GoogleSignInCallback() {
                @Override
                public void onSignInSuccess(String idToken) {
                    Timber.tag(TAG).d("Sign-in successful: %s", idToken);
                    loginViewModel.process(new LoginViewContract.Event.OnGoogleSignInSuccess(idToken));
                }

                @Override
                public void onSignInError(Exception e) {
                    Timber.tag(TAG).e(e, "Sign-in failed: %s", e.getMessage());
                    Toast.makeText(getContext(), "Sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Timber.tag(TAG).e(e, "Error during sign-in"); // Log the exception
            Toast.makeText(getContext(), "Error during sign-in: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        binding.loading.setVisibility(View.GONE);
    }
*/

    private void onForgotPasswordClicked() {
        String email = StringExtensions.getText(binding.loginEmailEditText);
        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Email field cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            loginViewModel.sendPasswordResetEmail(email);
        }
    }

    private TextWatcher setupTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* NO-OP */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /* NO-OP */ }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == binding.loginEmailEditText.getEditableText()) {
                    loginViewModel.process(new LoginViewContract.Event.OnUserNameTextChanged(s.toString()));
                } else if (s == binding.loginPasswordEditText.getEditableText()) {
                    loginViewModel.process(new LoginViewContract.Event.OnPasswordTextChanged(s.toString()));
                }
            }
        };
    }



}