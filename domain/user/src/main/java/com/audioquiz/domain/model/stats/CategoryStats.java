package com.adrian.model.stats;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the statistics of a user per category.
 * [soundWavesStats] The statistics of the user per sound waves category.
 * [synthesisStats] The statistics of the user per synthesis category.
 * [productionStats] The statistics of the user per production category.
 * [mixingStats] The statistics of the user per mixing category.
 * [processingStats] The statistics of the user per processing category.
 * [musicTheoryStats] The statistics of the user per music theory category.
 * [pitchStats] The statistics of the user per pitch category.
 * [intervalStats] The statistics of the user per interval category.
 */
public class CategoryStats {
    private String id;
    private Date lastUpdated;
    private CategoryStatsData soundWavesStats;
    private CategoryStatsData synthesisStats;
    private CategoryStatsData productionStats;
    private CategoryStatsData mixingStats;
    private CategoryStatsData processingStats;
    private CategoryStatsData musicTheoryStats;
    private CategoryStatsData pitchStats;
    private CategoryStatsData intervalStats;


    // No-argument constructor
    private CategoryStats() {
        this.lastUpdated = new Date();
        this.soundWavesStats = CategoryStatsData.createDefault("SoundWaves");
        this.synthesisStats = CategoryStatsData.createDefault("Synthesis");
        this.productionStats = CategoryStatsData.createDefault("Production");
        this.mixingStats = CategoryStatsData.createDefault("Mixing");
        this.processingStats = CategoryStatsData.createDefault("Processing");
        this.musicTheoryStats = CategoryStatsData.createDefault("MusicTheory");
        this.pitchStats = CategoryStatsData.createDefault("Pitch");
        this.intervalStats = CategoryStatsData.createDefault("Interval");
    }

    // Factory method to create a default instance
    public static CategoryStats createDefault(String id) {
        CategoryStats categoryStats = new CategoryStats();
        categoryStats.setId(id);
        return new CategoryStats();
    }

    // Constructor
    private CategoryStats(Builder builder) {
        this.id = builder.id;
        this.lastUpdated = builder.lastUpdated;
        this.soundWavesStats = builder.soundWavesStats;
        this.synthesisStats = builder.synthesisStats;
        this.productionStats = builder.productionStats;
        this.mixingStats = builder.mixingStats;
        this.processingStats = builder.processingStats;
        this.musicTheoryStats = builder.musicTheoryStats;
        this.pitchStats = builder.pitchStats;
        this.intervalStats = builder.intervalStats;
    }

    //GETTERS
    public String getId() {
        return id;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public CategoryStatsData getSoundWavesStats() {
        return soundWavesStats;
    }
    public CategoryStatsData getSynthesisStats() {
        return synthesisStats;
    }
    public CategoryStatsData getProductionStats() {
        return productionStats;
    }
    public CategoryStatsData getMixingStats() {
        return mixingStats;
    }
    public CategoryStatsData getProcessingStats() {
        return processingStats;
    }
    public CategoryStatsData getMusicTheoryStats() {
        return musicTheoryStats;
    }
    public CategoryStatsData getPitchStats() {
        return pitchStats;
    }
    public CategoryStatsData getIntervalStats() {
        return intervalStats;
    }
    //SETTERS
    public void setId(String id) {
        this.id = id;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setSoundWavesStats(CategoryStatsData soundWavesStats) {
        this.soundWavesStats = soundWavesStats;
    }
    public void setSynthesisStats(CategoryStatsData synthesisStats) {
        this.synthesisStats = synthesisStats;
    }
    public void setProductionStats(CategoryStatsData productionStats) {
        this.productionStats = productionStats;
    }
    public void setMixingStats(CategoryStatsData mixingStats) {
        this.mixingStats = mixingStats;
    }
    public void setProcessingStats(CategoryStatsData processingStats) {
        this.processingStats = processingStats;
    }
    public void setMusicTheoryStats(CategoryStatsData musicTheoryStats) {
        this.musicTheoryStats = musicTheoryStats;
    }
    public void setPitchStats(CategoryStatsData pitchStats) {
        this.pitchStats = pitchStats;
    }
    public void setIntervalStats(CategoryStatsData intervalStats) {
        this.intervalStats = intervalStats;
    }

    //BUILDER
    public static class Builder {
        private String id;
        private Date lastUpdated;
        private CategoryStatsData soundWavesStats;
        private CategoryStatsData synthesisStats;
        private CategoryStatsData productionStats;
        private CategoryStatsData mixingStats;
        private CategoryStatsData processingStats;
        private CategoryStatsData musicTheoryStats;
        private CategoryStatsData pitchStats;
        private CategoryStatsData intervalStats;

        public Builder() {
            // No-argument constructor
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }
        public Builder setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }
        public Builder setSoundWavesStats(CategoryStatsData soundWavesStats) {
            this.soundWavesStats = soundWavesStats;
            return this;
        }
        public Builder setSynthesisStats(CategoryStatsData synthesisStats) {
            this.synthesisStats = synthesisStats;
            return this;
        }
        public Builder setProductionStats(CategoryStatsData productionStats) {
            this.productionStats = productionStats;
            return this;
        }
        public Builder setMixingStats(CategoryStatsData mixingStats) {
            this.mixingStats = mixingStats;
            return this;
        }
        public Builder setProcessingStats(CategoryStatsData processingStats) {
            this.processingStats = processingStats;
            return this;
        }
        public Builder setMusicTheoryStats(CategoryStatsData musicTheoryStats) {
            this.musicTheoryStats = musicTheoryStats;
            return this;
        }
        public Builder setPitchStats(CategoryStatsData pitchStats) {
            this.pitchStats = pitchStats;
            return this;
        }
        public Builder setIntervalStats(CategoryStatsData intervalStats) {
            this.intervalStats = intervalStats;
            return this;
        }

        public CategoryStats build() {
            return new CategoryStats(this);
        }
    }

    // Helper methods to get and set com.example.model.domain.CategoryStatsData
    public CategoryStatsData getCategoryStatsData(String category) {
        switch (category.toLowerCase()) {
            case "soundwaves": return soundWavesStats;
            case "synthesis": return synthesisStats;
            case "production": return productionStats;
            case "mixing": return mixingStats;
            case "processing": return processingStats;
            case "musictheory": return musicTheoryStats;
            case "pitch": return pitchStats;
            case "interval": return intervalStats;
            default: return null;
        }
    }

    public void setCategoryStatsData(String category, CategoryStatsData data) {
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

    public Map<String, CategoryStatsData> getAllCategoryStatsData() {
        Map<String, CategoryStatsData> allData = new HashMap<>();
        allData.put("soundwaves", soundWavesStats);
        allData.put("synthesis", synthesisStats);
        allData.put("production", productionStats);
        allData.put("mixing", mixingStats);
        allData.put("processing", processingStats);
        allData.put("musictheory", musicTheoryStats);
        allData.put("pitch", pitchStats);
        allData.put("interval", intervalStats);
        return allData;
    }


    @Override
    public String toString() {
        return "com.example.model.domain.CategoryStats{" +
                ", soundWavesStats=" + soundWavesStats +
                ", synthesisStats=" + synthesisStats +
                ", productionStats=" + productionStats +
                ", mixingStats=" + mixingStats +
                ", processingStats=" + processingStats +
                ", musicTheoryStats=" + musicTheoryStats +
                ", pitchStats=" + pitchStats +
                ", intervalStats=" + intervalStats +
                '}';
    }

    /*
    // Method to update category stats based on quiz result

    public com.example.model.domain.CategoryStats updateCategoryStats(com.example.model.domain.QuizResult quizResult) {
        Log.d(TAG, "Updating category stats");
        String quizType = quizResult.getQuizType();
        String category = quizResult.getCategory();
        com.example.model.domain.CategoryStats newCategoryStats = com.example.model.domain.CategoryStats.createDefault();

        if (quizType.equalsIgnoreCase("LESSON")) {
            newCategoryStats = updateLessonProgression(quizResult);
        } else if (quizType.equalsIgnoreCase("COMPETITIVE")) {
            newCategoryStats = updateCompetitiveProgression(quizResult);
        } else if (category.equalsIgnoreCase("pitch")) {
            newCategoryStats = updateCompetitiveProgression(quizResult);
        } else {
            Log.d(TAG, "An error has occurred: " + quizResult.getCategory() + quizResult.getChapter());
        }
        return newCategoryStats;
    }


    private com.example.model.domain.CategoryStats updateLessonProgression(com.example.model.domain.QuizResult quizResult) {
        int score = quizResult.getScore();
        String category = quizResult.getCategory();
        int chapter = quizResult.getChapter();
        com.example.model.domain.CategoryStatsData categoryStatsData = getCategoryStatsData(category);

        int currentChapter = categoryStatsData != null ? categoryStatsData.getCurrentChapter() : 1;
        int numberOfQuizzes = categoryStatsData != null ? categoryStatsData.getNumberOfQuizzes() : 1;
        int correctAnswersLearn = categoryStatsData != null ? categoryStatsData.getCorrectAnswersLearn() : 1;
        int totalQuestionsLearn = categoryStatsData != null ? categoryStatsData.getTotalQuestionsLearn() : 1;

        if (chapter < 4) {
            totalQuestionsLearn += 7;
            if (quizResult.getScore() > 2) {
                currentChapter += 1;
            } else {
                Log.d(TAG, "Score is less than passing score");
            }
        } else if (chapter == 4) {
            totalQuestionsLearn += 15;
            if (score > 11) {
                currentChapter += 1;
            } else {
                Log.d(TAG, "Score is less than passing score");
            }
        } else {
            Log.d(TAG, "Chapter is greater than 4");
        }

        com.example.model.domain.CategoryStatsData updatedProgressionData = new com.example.model.domain.CategoryStatsData.Builder()
                .setCurrentChapter(currentChapter)
                .setNumberOfQuizzes(numberOfQuizzes)
                .setTotalQuestionsLearn(totalQuestionsLearn)
                .setCorrectAnswersLearn(correctAnswersLearn)
                .build();

        setCategoryStatsData(category, updatedProgressionData);

        return this;
    }

    private com.example.model.domain.CategoryStats updateCompetitiveProgression(com.example.model.domain.QuizResult quizResult) {
        Map<String, Integer> scorePerCategory = quizResult.getScorePerCategory();
        Log.d(TAG, "Updating competitive progression");
        for (Map.Entry<String, com.example.model.domain.CategoryStatsData> entry : getAllCategoryStatsData().entrySet()) {
            String category = entry.getKey();
            com.example.model.domain.CategoryStatsData statsData = entry.getValue();

            if (scorePerCategory.containsKey(category)) {
                Integer scoreInteger = scorePerCategory.get(category);
                int score = scoreInteger != null ? scoreInteger : 0;
                statsData.setCorrectAnswersCompetitive(statsData.getCorrectAnswersCompetitive() + score);
                statsData.setTotalQuestionsCompetitive(statsData.getTotalQuestionsCompetitive() + 10);
            }
        }

        return this;
    }



    public static com.example.model.domain.CategoryStats fromMaap(Map<String, Map<String, Integer>> categoryStatsMap) {
        com.example.model.domain.CategoryStats categoryStats = new com.example.model.domain.CategoryStats();
        categoryStats.setSoundWavesStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("soundWavesStats"));
        categoryStats.setSynthesisStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("synthesisStats"));
        categoryStats.setProductionStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("productionStats"));
        categoryStats.setMixingStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("mixingStats"));
        categoryStats.setProcessingStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("processingStats"));
        categoryStats.setMusicTheoryStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("musicTheoryStats"));
        categoryStats.setFrequencyStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("pitchStats"));
        categoryStats.setIntervalStats((com.example.model.domain.CategoryStatsData) categoryStatsMap.get("intervalStats"));
        return categoryStats;
    }

    public static com.example.model.domain.CategoryStats fromMap(Map<String, Object> categoryStatsMap) {
        return new com.example.model.domain.CategoryStats.Builder()
                .setSoundWavesStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("soundWavesStats")))
                .setSynthesisStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("synthesisStats")))
                .setProductionStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("productionStats")))
                .setMixingStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("mixingStats")))
                .setProcessingStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("processingStats")))
                .setMusicTheoryStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("musicTheoryStats")))
                .setFrequencyStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("pitchStats")))
                .setIntervalStats(com.example.model.domain.CategoryStatsData.fromMap((Map<String, Object>) categoryStatsMap.get("intervalStats")))
                .build();
    }*/
}
