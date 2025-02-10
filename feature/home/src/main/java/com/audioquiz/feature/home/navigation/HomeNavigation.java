package com.audioquiz.feature.home.navigation;

import android.os.Bundle;

public interface HomeNavigation {
    void navigateHomeToSettings();
    void navigateHomeToQuiz(Bundle args);
    void navigateHomeToCategorySheet(Bundle args);
}
