package com.audioquiz.designsystem.extensions;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.audioquiz.designsystem.R;
import com.audioquiz.designsystem.animations.TransactionAnimations;
import com.audioquiz.designsystem.animations.TransactionType;


public class FragmentTransactionExtensions {
    /**
     * Extension for add or replace the fragment to the container.
     * [TransactionType.ADD_FRAGMENT] - Will put the [newFragment] on top of the current fragment.
     * [TransactionType.REPLACE_FRAGMENT] - Will replace the current fragment for the [newFragment] & the current fragment would not be visible.
     *
     * @param transactionType a [TransactionType] object.
     * @param newFragment the fragment to ADD or REPLACE.
     * @param addToBackStack if true then, the [newFragment] will be added to the back stack.
     * @param containerId the VIEW container id. The ID passed here it's supposed to be the ID of the View that contains the Fragment/s. Usually a FrameLayout or a FragmentContainerView.
     * @param transactionAnimations a [TransactionAnimations] object that will determine the animation to apply to the current transaction.
     */
    public static void fragmentTransactionExt(
            FragmentTransaction transaction,
            TransactionType transactionType,
            Fragment newFragment,
            boolean addToBackStack,
            int containerId,
            String fragmentTag,
            TransactionAnimations transactionAnimations
    ) {
        Log.i(
                "TransactionExt",
                "transactionType: " + transactionType + " | addToBackStack: " + addToBackStack + " | containerId: " + containerId + " | fragmentTag: " + fragmentTag + " | transactionAnimations: " + transactionAnimations
        );

        switch (transactionAnimations) {
            case BOTTOM_TO_TOP:
                transaction.setCustomAnimations(
                        R.anim.slide_in_from_bottom, R.anim.slide_out_from_top,
                        R.anim.slide_in_from_bottom, R.anim.slide_out_from_top
                );
                break;
            case RIGHT_TO_LEFT:
                transaction.setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right
                );
                break;
            case NONE:
                // None transition animation is applied
                break;
        }

        switch (transactionType) {
            case ADD_FRAGMENT:
                transaction.add(containerId, newFragment, fragmentTag);
                break;
            case REPLACE_FRAGMENT:
                transaction.replace(containerId, newFragment, fragmentTag);
                break;
        }

        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }

        transaction.commit();
    }
}