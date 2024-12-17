package com.audioquiz.designsystem.model;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CategoryStatsUi {
    private String id;
    private Date lastUpdated;
    private CategoryStatsDataUi soundWavesStats;
    private CategoryStatsDataUi synthesisStats;
    private CategoryStatsDataUi productionStats;
    private CategoryStatsDataUi mixingStats;
    private CategoryStatsDataUi processingStats;
    private CategoryStatsDataUi musicTheoryStats;
    private CategoryStatsDataUi pitchStats;
    private CategoryStatsDataUi intervalStats;


    // No-argument constructor
    private CategoryStatsUi() {
    }

    private CategoryStatsUi(Builder builder) {
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
    public CategoryStatsDataUi getSoundWavesStats() {
        return soundWavesStats;
    }
    public CategoryStatsDataUi getSynthesisStats() {
        return synthesisStats;
    }
    public CategoryStatsDataUi getProductionStats() {
        return productionStats;
    }
    public CategoryStatsDataUi getMixingStats() {
        return mixingStats;
    }
    public CategoryStatsDataUi getProcessingStats() {
        return processingStats;
    }
    public CategoryStatsDataUi getMusicTheoryStats() {
        return musicTheoryStats;
    }
    public CategoryStatsDataUi getPitchStats() {
        return pitchStats;
    }
    public CategoryStatsDataUi getIntervalStats() {
        return intervalStats;
    }
    //SETTERS
    public void setId(String id) {
        this.id = id;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setSoundWavesStats(CategoryStatsDataUi soundWavesStats) {
        this.soundWavesStats = soundWavesStats;
    }
    public void setSynthesisStats(CategoryStatsDataUi synthesisStats) {
        this.synthesisStats = synthesisStats;
    }
    public void setProductionStats(CategoryStatsDataUi productionStats) {
        this.productionStats = productionStats;
    }
    public void setMixingStats(CategoryStatsDataUi mixingStats) {
        this.mixingStats = mixingStats;
    }
    public void setProcessingStats(CategoryStatsDataUi processingStats) {
        this.processingStats = processingStats;
    }
    public void setMusicTheoryStats(CategoryStatsDataUi musicTheoryStats) {
        this.musicTheoryStats = musicTheoryStats;
    }
    public void setPitchStats(CategoryStatsDataUi pitchStats) {
        this.pitchStats = pitchStats;
    }
    public void setIntervalStats(CategoryStatsDataUi intervalStats) {
        this.intervalStats = intervalStats;
    }

    //BUILDER
    public static class Builder {
        private String id;
        private Date lastUpdated;
        private CategoryStatsDataUi soundWavesStats;
        private CategoryStatsDataUi synthesisStats;
        private CategoryStatsDataUi productionStats;
        private CategoryStatsDataUi mixingStats;
        private CategoryStatsDataUi processingStats;
        private CategoryStatsDataUi musicTheoryStats;
        private CategoryStatsDataUi pitchStats;
        private CategoryStatsDataUi intervalStats;

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
        public Builder setSoundWavesStats(CategoryStatsDataUi soundWavesStats) {
            this.soundWavesStats = soundWavesStats;
            return this;
        }
        public Builder setSynthesisStats(CategoryStatsDataUi synthesisStats) {
            this.synthesisStats = synthesisStats;
            return this;
        }
        public Builder setProductionStats(CategoryStatsDataUi productionStats) {
            this.productionStats = productionStats;
            return this;
        }
        public Builder setMixingStats(CategoryStatsDataUi mixingStats) {
            this.mixingStats = mixingStats;
            return this;
        }
        public Builder setProcessingStats(CategoryStatsDataUi processingStats) {
            this.processingStats = processingStats;
            return this;
        }
        public Builder setMusicTheoryStats(CategoryStatsDataUi musicTheoryStats) {
            this.musicTheoryStats = musicTheoryStats;
            return this;
        }
        public Builder setPitchStats(CategoryStatsDataUi pitchStats) {
            this.pitchStats = pitchStats;
            return this;
        }
        public Builder setIntervalStats(CategoryStatsDataUi intervalStats) {
            this.intervalStats = intervalStats;
            return this;
        }

        public CategoryStatsUi build() {
            return new CategoryStatsUi(this);
        }
    }

    public CategoryStatsDataUi getCategoryStatsDataUi(String category) {
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

    public void setCategoryStatsData(String category, CategoryStatsDataUi data) {
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

    public Map<String, CategoryStatsDataUi> getAllCategoryStatsData() {
        Map<String, CategoryStatsDataUi> allData = new HashMap<>();
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
}
