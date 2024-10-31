package com.audioquiz.core.domain.repository.quiz;

public interface QuizPlayerRepository {
    int getUserLives(UserLivesObserver observer);

    interface UserLivesObserver {
        void onUserLivesChanged(int userLives);
        void onError(Exception e);
    }
}
