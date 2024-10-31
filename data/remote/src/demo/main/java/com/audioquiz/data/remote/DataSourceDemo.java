package com.audioquiz.data.remote.service;

import android.app.Application;

import com.audioquiz.api.datasources.firebase.FirebaseApi;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.audioquiz.data.remote.provider.StorageProvider;

import javax.inject.Inject;

public class DataSourceDemo implements  {
    private final userId = "DemoUser0101"
    private final UserProfileDto userProfileDto;
    private final GeneralStatsDto generalStatsDto;
    private final CategoryStatsDto categoryStatsDto;
    private final WeeklyStatsDto weeklyStatsDto;
    private final List<RankEntryDto> rankEntries;

    private Random random = new Random();


    @Inject
    public DataSourceDemo() {
        init();
    }

    private void init() {
        setUserProfileDto();
        setGeneralStatsDto();
        setCategoryStatsDto();
        setWeeklyStatsDto();
        setRankEntries();
    }

    private void setUserProfileDto() {
        userProfileDto.setLastUpdated(new Date());
        userProfileDto.setUserId(userId);
        userProfileDto.setUsername(TextUtils.getRandomUsername());
        userProfileDto.setProfileImage(TextUtils.getRandomProfileImage());
        userProfileDto.setDateCreated("2024-03-27");
    }

    private void setGeneralStatsDto() {
        generalStatsDto.setUserLevel(random.nextInt(100));
        generalStatsDto.setNumberOfLives(random.nextInt(5));
        generalStatsDto.setNumberOfQuizzes(random.nextInt(50));
        generalStatsDto.setNumberOfQuestions(random.nextInt(200));
        generalStatsDto.setTotalScore(random.nextInt(1000));
        generalStatsDto.setAverageScore(random.nextDouble() * 100);
        generalStatsDto.setCurrentStreak(random.nextInt(10));
        generalStatsDto.setLongestStreak(random.nextInt(20));
        generalStatsDto.setLastQuizDate(new Date());
    }

    private void setCategoryStatsDto() {
        // Initialize categoryStatsDto
    }

    private void getCategoryStatsDataDto() {
        CategoryStatsDataDto categoryStatsDto = new CategoryStatsDataDto();

        public int categoryIndex;
        public String categoryName;
        public int currentChapter;
        public int numberOfQuizzes;
        public int totalQuestionsLearn;
        public int correctAnswersLearn;
        public int totalQuestionsCompetitive;
        public int correctAnswersCompetitive;
        public double totalTimeSpent;

        categoryStatsDto.setCategoryIndex(userId);

    }

    private static final List<String> categories = Arrays.asList("Soundwaves", "Synthesis", "Production", "Mixing", "Processing", "MusicTheory", "Pitch", "Interval");

    public void setCategoryStats(String category, CategoryStatsData data) {
        switch (category.toLowerCase()) {
            case "soundwaves": soundWavesStats = data; break;
            case "synthesis": synthesisStats = data; break;
            case "production": productionStats = data; break;
            case "mixing": mixingStats = data; break;
            case "processing": processingStats = data; break;
            case "musictheory": musicTheoryStats = data; break;
            case "pitch": pitchStats = data; break;
            case "interval": intervalStats = data; break;
            default: break;
        }
    }

    private void setWeeklyStatsDto() {
        // Initialize weeklyStatsDto
    }

    private void setRankEntries() {
        // Initialize rankEntries
    }

    public UserProfileDto getUserProfileDto() {
        return userProfileDto;
    }

    public GeneralStatsDto getGeneralStatsDto() {
        return generalStatsDto;
    }

    public CategoryStatsDto getCategoryStatsDto() {
        return categoryStatsDto;
    }

    public WeeklyStatsDto getWeeklyStatsDto() {
        return weeklyStatsDto;
    }

    public List<RankEntryDto> getRankEntries() {
        return rankEntries;
    }




}