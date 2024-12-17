package com.audioquiz.feature.login.sign_up;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.audioquiz.feature.login.R;
import com.audioquiz.feature.login.databinding.FragmentSignupBinding;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class SignupFragment extends Fragment {
    private static final String TAG = "SignupFragment";
    private SignupViewModel signupViewModel;
    private FragmentSignupBinding binding;

    @Inject
    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        binding.setViewModel(signupViewModel);
        Timber.tag(TAG).d("Signup Fragment created");
        binding.btnBackSignup.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.btnBackSignup.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.btnSignup.setOnClickListener(v -> {
            signupViewModel.registerUser(
                    Objects.requireNonNull(binding.signupEmailEditText.getText()).toString(),
                    Objects.requireNonNull(binding.signupPasswordEditText.getText()).toString(),
                    Objects.requireNonNull(binding.signupUsernameEditText.getText()).toString()
            );
            showConfirmationDialog();
        });

        binding.btnSignupToLogin.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_signupFragment_to_loginFragment));

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeRegistrationErrorMessage();
    }

    private void observeRegistrationErrorMessage() {
        signupViewModel.getRegistrationErrorMessage().observe(getViewLifecycleOwner(), error ->
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show());
    }

    private void showConfirmationDialog() {
        signupViewModel.showConfirmationDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("We have sent you a confirmation email. We're nearly there.")
                .setPositiveButton("I have confirmed my email", (dialog, which) ->
                        signupViewModel.getIsRegistered().observe(getViewLifecycleOwner(), isRegistered -> {
                            if (Boolean.TRUE.equals(isRegistered)) {
                                Navigation.findNavController(requireView()).navigate(R.id.action_signupFragment_to_loginFragment);
                            } else {
                                Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }))
                .setNegativeButton("Resend Email", (dialog, which) ->
                        signupViewModel.getFirebaseUserLiveData().observe(getViewLifecycleOwner(), user -> {
                            if (user != null) {
                                signupViewModel.resendVerificationEmail();
                            }
                        }))
                .show();
    }
}