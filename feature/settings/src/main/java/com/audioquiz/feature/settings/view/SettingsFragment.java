package com.audioquiz.feature.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewState;
import com.audioquiz.feature.settings.databinding.FragmentSettingsBinding;
import com.audioquiz.feature.settings.domain.SettingsViewContract;
import com.audioquiz.feature.settings.presentation.navigation.SettingsCoordinatorEvent;
import com.audioquiz.feature.settings.presentation.navigation.SettingsFlowCoordinator;
import com.audioquiz.feature.settings.view.viewmodel.SettingsViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;


    @Inject
    SettingsFlowCoordinator settingsFlowCoordinator;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        // Initialize UI components and event listeners
        initializeComponents();
        observeViewModel();

        return binding.getRoot();
    }

    private void observeViewModel() {
        viewModel.navigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);
        viewModel.getViewState().observe(getViewLifecycleOwner(), this::onViewStateChanged);
        viewModel.getViewEffect().observe(getViewLifecycleOwner(), this::onViewEffectReceived);
        // Observers for logout and account deletion
        viewModel.getLogoutSuccess().observe(getViewLifecycleOwner(), isLoggedOut -> {
            if (Boolean.TRUE.equals(isLoggedOut)) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        viewModel.getIsAccountDeleted().observe(getViewLifecycleOwner(), isDeleted -> {
            if (Boolean.TRUE.equals(isDeleted)) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });
    }

    private void initializeComponents() {  // Set up confirm change button
        binding.btnConfirmChange.setOnClickListener(v -> confirmChanges(binding.usernameChangeEditText, binding.emailChangeEditText));

        // Get and display the current user's details
        String currentUserName = viewModel.getCurrentUserName();
        String currentUserEmail = viewModel.getCurrentUserEmail();
        if (currentUserName != null) {
            binding.usernameChangeEditText.setHint(currentUserName);
            binding.emailChangeEditText.setHint(currentUserEmail);
        }

        // Password change button
        binding.btnChangePassword.setOnClickListener(v -> setupPasswordChangeSheet());

        // Logout button
        binding.btnLogout.setOnClickListener(v -> viewModel.process(new SettingsViewContract.Event.OnLogoutButtonClicked()));

        // Delete account button
        binding.btnDeleteAccount.setOnClickListener(v -> setupDeleteAccountDialog());
    }

    private void handleNavigation(SettingsCoordinatorEvent event) {
        if (settingsFlowCoordinator != null) {
            settingsFlowCoordinator.onEvent(event);
        } else {
            Timber.tag("LoginFragment").e("LoginFlowCoordinator is null");
        }
    }

    protected void onViewStateChanged(ViewState viewState) {
        if (viewState instanceof SettingsViewContract.State state) {
            binding.btnConfirmChange.setEnabled(state.isDataValid());
            binding.btnLogout.setEnabled(!state.isLoading());
            binding.btnChangePassword.setEnabled(!state.isLoading());
            binding.btnDeleteAccount.setEnabled(!state.isLoading());
        }
    }
    protected void onViewEffectReceived(ViewEffect viewEffect) {
        if (viewEffect instanceof SettingsViewContract.Effect.ShowErrorToast showErrorToast) {
            Toast.makeText(getContext(), showErrorToast.getError(), Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmChanges(TextInputEditText usernameEditText, TextInputEditText emailEditText) {
        String newUsername = Objects.requireNonNull(usernameEditText.getText()).toString();
        String newEmail = Objects.requireNonNull(emailEditText.getText()).toString();
        viewModel.updateEmail(newEmail);
        viewModel.updateUsername(newUsername);
    }

    private void setupPasswordChangeSheet() {
        PasswordChangeSheetFragment bottomSheet = new PasswordChangeSheetFragment();
        bottomSheet.show(getChildFragmentManager(), "passwordBottomSheet");
    }

    private void setupDeleteAccountDialog() {
        DeleteAccountDialogFragment deleteAccountDialogFragment = new DeleteAccountDialogFragment();
        deleteAccountDialogFragment.setOnPasswordConfirmedListener(password -> {
            if (password != null && !password.isEmpty()) {
                viewModel.deleteUserAccount(password);
            } else {
                Timber.tag(TAG).d("Failed to delete user account: Password is missing.");
            }
        });
        deleteAccountDialogFragment.show(getChildFragmentManager(), "deleteAccountDialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}