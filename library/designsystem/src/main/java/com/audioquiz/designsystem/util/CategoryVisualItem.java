package com.audioquiz.designsystem.util;


import com.audioquiz.designsystem.R;

public enum CategoryVisualItem {
    SoundWaves(R.drawable.ic_soundwaves, R.drawable.ic_badge_soundwaves, R.array.soundwaves_chapters, R.array.soundwaves_descriptions),
    Synthesis(R.drawable.ic_synthesis, R.drawable.ic_badge_synthesis, R.array.synthesis_chapters, R.array.synthesis_descriptions),
    Production(R.drawable.ic_production, R.drawable.ic_badge_production, R.array.production_chapters, R.array.production_descriptions),
    Mixing(R.drawable.ic_mixing, R.drawable.ic_badge_mixing, R.array.mixing_chapters, R.array.mixing_descriptions),
    Processing(R.drawable.ic_processing, R.drawable.ic_badge_processing, R.array.processing_chapters, R.array.processing_descriptions),
    MusicTheory(R.drawable.ic_music_theory, R.drawable.ic_badge_music_theory, R.array.musictheory_chapters, R.array.musictheory_descriptions),
    Interval(R.drawable.ic_badge_intervals, R.drawable.ic_badge_intervals, -1, -1),
    Pitch(R.drawable.ic_badge_pitch, R.drawable.ic_badge_pitch, -1, -1);

    private final int icon;
    private final int badge;
    private final int chapterNamesArray;
    private final int descriptionArray;

    CategoryVisualItem(int icon, int badge, int chapterNamesArray, int descriptionArray) {
        this.icon = icon;
        this.badge = badge;
        this.chapterNamesArray = chapterNamesArray;
        this.descriptionArray = descriptionArray;
    }

    public int getIcon() {
        return icon;
    }

    public int getBadge() {
        return badge;
    }

    public int getChapterNamesArray() {
        return chapterNamesArray;
    }

    public int getDescriptionArray() {
        return descriptionArray;
    }
    }

