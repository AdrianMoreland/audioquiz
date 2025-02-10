package com.audioquiz.feature.home.domain;

import com.audioquiz.designsystem.base.view.ViewEffect;
import com.audioquiz.designsystem.base.view.ViewEvent;

import java.util.List;

import timber.log.Timber;

public class HomeViewContract {

    public record State(
            boolean isLoading, List<CategoryCardState> categories, boolean showBadges,
                        boolean isBottomSheetVisible) {
    }

    public static class CategoryCardState {
        public final int categoryIndex;
        public final String name;
        public final int currentChapter;
        public final Integer descriptionResId;
        public final Integer chaptersResId;
        public final Integer imageResId;
        public final Integer badgeResId;

        // Constructor for BottomSheetCategory
        public CategoryCardState(int categoryIndex,
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

    public static abstract class Event implements ViewEvent {

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

        public static class OnStartQuizTriggered extends Event {
            private final String category;
            private final int chapter;

            public OnStartQuizTriggered(String category, int chapter) {
                this.category = category;
                this.chapter = chapter;
            }
            public String getCategory() {
                return category;
            }
            public int getChapter() {
                return chapter;
            }

        }

        public static class OnSettingsButtonClicked extends Event {}

        public static class OnViewBadgesButtonClicked extends Event {}


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

        public static class NavigateToSettings extends Effect {
            public NavigateToSettings() {
                // Empty constructor
            }
        }
    }

}

//region TRASH
/*public static class ImageResources {
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
}*/
//endRegion