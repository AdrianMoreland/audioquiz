package com.audioquiz.designsystem.extensions;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.audioquiz.designsystem.R;
import com.audioquiz.designsystem.animations.TransactionAnimations;
import com.google.android.material.snackbar.Snackbar;

public class FragmentExtension {

    public static int getColor(Fragment fragment, @ColorRes int colorRes) {
        return ContextCompat.getColor(fragment.requireContext(), colorRes);
    }

    public Drawable getDrawable(Fragment fragment, @DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(fragment.requireContext(), drawableRes);
    }


    /**
     * Show a Snackbar with a message.
     */
    public static void showSnackBar(Fragment fragment, String message) {
        View view = fragment.getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Show a Snackbar with a message and an action.
     */
    public static void showSnackBarWithAction(Fragment fragment, String message, String actionText, View.OnClickListener action) {
        View view = fragment.getView();
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction(actionText, action)
                    .show();
        }
    }

    /**
     * Get a String resource.
     */
    public static String getString(Fragment fragment, @StringRes int resId) {
        return fragment.requireContext().getString(resId);
    }

    /**
     * Get a String resource with formatted arguments.
     */
    public static String getString(Fragment fragment, @StringRes int resId, Object... formatArgs) {
        return fragment.requireContext().getString(resId, formatArgs);
    }

    //region Fragment Transactions
    public static void navigateSafe(Fragment fragment, NavDirections directions, NavOptions navOptions) {
        if (canNavigate(fragment)) {
            NavHostFragment.findNavController(fragment).navigate(directions, navOptions);
        }
    }

    public static boolean canNavigate(Fragment fragment) {
        NavController navController = NavHostFragment.findNavController(fragment);
        Integer destinationIdInNavController = navController.getCurrentDestination() != null ? navController.getCurrentDestination().getId() : null;

        // add unique tag_navigation_destination_id to the res\values\ids.xml:
        Object destinationIdOfThisFragment = fragment.getView() != null ? fragment.getView().getTag(R.id.tag_navigation_destination_id) : destinationIdInNavController;

        // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
        if (destinationIdInNavController != null && destinationIdInNavController.equals(destinationIdOfThisFragment)) {
            if (fragment.getView() != null) {
                fragment.getView().setTag(R.id.tag_navigation_destination_id, destinationIdOfThisFragment);
            }
            return true;
        } else {
            Log.d("FragmentExtension", "cannot navigate, fragment is no longer in the current navigation graph.");
            return false;
        }
    }

    /**
     * Pop the back stack using Jetpack Navigation.*/
    public static void pop(Fragment fragment) {
        NavController navController = NavHostFragment.findNavController(fragment);
        navController.popBackStack();
    }

    /**
     * Pop the back stack up to a specific destination using Jetpack Navigation.
     */
    public static void popUntil(Fragment fragment, int destinationId) {
        NavController navController = NavHostFragment.findNavController(fragment);
        navController.popBackStack(destinationId, false);
    }

    /**
     * Navigate to a new destination using Jetpack Navigation.
     *
     * @param currentFragment The current fragment.
     * @param destinationId The ID of the destination fragment.
     * @param addToBackStack Whether to add the navigation to the back stack.
     * @param transactionAnimations A [TransactionAnimations] object to determine the animation.
     * @param args Optional Bundle of arguments to pass to the destination fragment.
     */
    public static void navigateToFragment(
            Fragment currentFragment,
            @IdRes int destinationId,
            boolean addToBackStack,
            TransactionAnimations transactionAnimations,
            @Nullable Bundle args
    ) {NavController navController = NavHostFragment.findNavController(currentFragment);
        NavOptions.Builder navOptionsBuilder = new NavOptions.Builder();

        switch (transactionAnimations) {
            case BOTTOM_TO_TOP:
                navOptionsBuilder.setEnterAnim(R.anim.slide_in_from_bottom)
                        .setExitAnim(R.anim.slide_out_from_top)
                        .setPopEnterAnim(R.anim.slide_in_from_bottom)
                        .setPopExitAnim(R.anim.slide_out_from_top);
                break;
            case RIGHT_TO_LEFT:
                navOptionsBuilder.setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right);
                break;
            case NONE:
                // No transition animation is applied
                break;
        }

        if (addToBackStack) {
            navOptionsBuilder.setLaunchSingleTop(true); // Optional: Handle single top behavior
        }

        NavOptions navOptions = navOptionsBuilder.build();

        if (args != null) {
            navController.navigate(destinationId, args, navOptions);
        } else {
            navController.navigate(String.valueOf(destinationId), navOptions);
        }
    }
    //endregion
}



