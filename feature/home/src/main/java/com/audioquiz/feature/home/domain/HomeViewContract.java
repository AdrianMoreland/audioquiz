package com.audioquiz.feature.home.domain;

import android.os.Bundle;

import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewEvent;

import java.util.List;

import timber.log.Timber;

public class HomeViewContract {

    public enum ChapterEnum {
        CHAPTER_1,
        CHAPTER_2,
        CHAPTER_3,
        FINAL_EXAM,
        DONE
    }

    public static class State {
        private final boolean isLoading;
        private final List<CategoryUi> categories;
        private final boolean showBadges;
        private final boolean isBottomSheetVisible;


        public State(boolean isLoading, List<CategoryUi> categories, boolean showBadges, boolean isBottomSheetVisible) {
            this.isLoading = isLoading;
            this.categories = categories;
            this.showBadges = showBadges;
            this.isBottomSheetVisible = isBottomSheetVisible;
        }

        // Getters and setters for each property
        public boolean isLoading() {
            return isLoading;
        }

        public List<CategoryUi> getCategories() {
            return categories;
        }

        public boolean isShowBadges() {
            return showBadges;
        }

        public boolean isBottomSheetVisible() {
            return isBottomSheetVisible;
        }
    }

    public static class CategoryUi {
        public final int categoryIndex;
        public final String name;
        public final int currentChapter;
        public final Integer descriptionResId;
        public final Integer chaptersResId;
        public final Integer imageResId;
        public final Integer badgeResId;

        // Constructor for BottomSheetCategory
        public CategoryUi(int categoryIndex,
                          String name,
                          int currentChapter,
                          Integer imageResId,
                          Integer badgeResId,
                          Integer descriptionResId,
                          Integer chaptersResId) {
            this.categoryIndex = categoryIndex;
            this.name = name;
            this.currentChapter = currentChapter;
            this.imageResId = imageResId;
            this.badgeResId = badgeResId;
            this.descriptionResId = descriptionResId;
            this.chaptersResId = chaptersResId;
        }

        public String getName() {
            return name;
        }

        public int getCurrentChapter() {
            return currentChapter;
        }

        public int getDescriptionsResId() {
            return descriptionResId;
        }

        public int getChaptersResId() {
            return chaptersResId;
        }

        public int getImageResId() {
            Timber.tag("CategoryUi").d("getImageResId called with imageResId: %s", imageResId);
            return imageResId != null ? imageResId : com.audioquiz.designsystem.R.drawable.image_placeholder; // Default image
        }

        public Integer getBadgeResId() {
            return badgeResId;
        }

        public boolean isCompleted() {
            return getProgress() == 100;
        }

        public int getProgress() {
            return currentChapter > 0 ? ((currentChapter - 1) / getMaxChapter()) * 100 : 1;
        }
        public int getMaxChapter() {
            return 5;
        }

    }



    public static class ImageResources {
        public int imageResId;
        public int badgeResId;

        public ImageResources(int imageResId, int badgeResId) {
            this.imageResId = imageResId;
            this.badgeResId = badgeResId;
        }


        public int getImageResId() {
            return imageResId;
        }

        public int getBadgeResId() {
            return badgeResId;
        }
    }

    public static abstract class Event implements ViewEvent {
        Type getType() {
            return null;
        }

        enum Type {
            CATEGORY_CLICKED,
            SETTINGS_BUTTON_CLICKED,
            VIEW_BADGES_BUTTON_CLICKED,
            START_QUIZ_TRIGGERED
        }

        // Add specific data classes for events with data
        public static class OnCategoryCardClicked extends Event {
            private final String category;
            private final int currentChapter;

            public OnCategoryCardClicked(String category, int currentChapter) {
                this.category = category;
                this.currentChapter = currentChapter;
            }

            public String getCategory() {
                return category;
            }

            public int getCurrentChapter() {
                return currentChapter;
            }
        }

        public static class OnSettingsButtonClicked extends Event {
            @Override
            Type getType() {
                return Type.SETTINGS_BUTTON_CLICKED;
            }
        }

        public static class OnViewBadgesButtonClicked extends Event {
            @Override
            Type getType() {
                return Type.VIEW_BADGES_BUTTON_CLICKED;
            }
        }

        public static class OnStartQuizTriggered extends Event {
            @Override
            Type getType() {
                return Type.START_QUIZ_TRIGGERED;
            }
            private final Bundle bundle;

            public OnStartQuizTriggered(Bundle bundle) {
                this.bundle = bundle;
            }

            public Bundle getBundle() {
                return bundle;
            }

        }
    }

    public abstract static class Effect implements ViewEffect {

        public static class ShowCategoryBottomSheet extends Effect {
            private final String category;
            private final int currentChapter;

            public ShowCategoryBottomSheet(String category, int currentChapter) {
                this.category = category;
                this.currentChapter = currentChapter;
            }

            public String getCategory() {
                return category;
            }

            public int getCurrentChapter() {
                return currentChapter;
            }
        }

        public static class NavigateToQuiz extends Effect {
            private final String category;

            private final int currentChapter;
            public NavigateToQuiz(String category, int currentChapter) {
                this.category = category;
                this.currentChapter = currentChapter;
            }

            public String getCategory() {
                return category;
            }

            public int getCurrentChapter() {
                return currentChapter;
            }

        }

        public static class ShowErrorToast extends Effect {
            private final String error;

            public ShowErrorToast(String error) {
                this.error = error;
            }

            public String getError() {
                return error;
            }
        }
    }

}