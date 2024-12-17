package com.audioquiz.feature.home.domain;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        public final boolean isLoading;
        public final List<CategoryUi> categories;
        public final boolean showBadges;


        public State(boolean isLoading, List<CategoryUi> categories, boolean showBadges) {
            this.isLoading = isLoading;
            this.categories = categories;
            this.showBadges = showBadges;
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

    public static class Chapter {
        public int index;
        public ChapterEnum chapterEnum;
        TextView title;
        TextView description;
        LinearLayout container;
        Button button;

        public Chapter(int index, TextView title, TextView description, LinearLayout container, Button button) {
            this.index = index;
            this.title = title;
            this.description = description;
            this.container = container;
            this.button = button;
        }
    }

    public static class ChapterResources {
        public int chaptersResId;
        public int descriptionsResId;

        public ChapterResources(int chaptersResId, int descriptionsResId) {
            this.chaptersResId = chaptersResId;
            this.descriptionsResId = descriptionsResId;
        }

        public int getChaptersResId() {
            return chaptersResId;
        }

        public int getDescriptionsResId() {
            return descriptionsResId;
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
            VIEW_BADGES_BUTTON_CLICKED
        }

        // Add specific data classes for events with data
        public static class CategoryClicked extends Event {
            private final String category;
            private final int currentChapter;

            public CategoryClicked(String category, int currentChapter) {
                this.category = category;
                this.currentChapter = currentChapter;
            }

            @Override
            Type getType() {
                return Type.CATEGORY_CLICKED;
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
    }

    public static abstract class Effect implements ViewEffect {
        private final Type type;

        private Effect(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        public enum Type {
            SHOW_CATEGORY_BOTTOM_SHEET,
            SHOW_SETTINGS_DIALOG,
            SHOW_ERROR
        }

        // Add specific data classes for effects with data
        public static class ShowCategoryBottomSheet extends Effect {
            private final String category;
            private final int currentChapter;

            public ShowCategoryBottomSheet(String category, int currentChapter) {
                super(Type.SHOW_CATEGORY_BOTTOM_SHEET);
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
                super(Type.SHOW_ERROR);
                this.error = error;
            }

            public String getError() {
                return error;
            }
        }
    }

}