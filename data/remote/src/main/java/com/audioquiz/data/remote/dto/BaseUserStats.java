package com.audioquiz.data.remote.dto;

import com.google.firebase.Timestamp;

public class BaseUserStats {
        protected Id id;
        protected Timestamp lastUpdated;
        protected GeneralStatsDto generalStatsDto;
        protected CategoryStatsDto categoryStatsDto;
        protected FrequencyStatsDto frequencyStatsDto;
        protected Last7DaysScoresDto last7DaysScoresDto;


        public BaseUserStats(Id id, Timestamp lastUpdated, GeneralStatsDto generalStatsDto, CategoryStatsDto categoryStatsDto, FrequencyStatsDto frequencyStatsDto, Last7DaysScoresDto last7DaysScoresDto) {
            this.id = id;
            this.lastUpdated = lastUpdated;
            this.generalStatsDto = generalStatsDto;
            this.categoryStatsDto = categoryStatsDto;
            this.frequencyStatsDto = frequencyStatsDto;
            this.last7DaysScoresDto = last7DaysScoresDto;
        }

    public BaseUserStats() {

    }

    // getters and setters
        public Id getId() {
            return id;
        }

        public void setId(Id id) {
            this.id = id;
        }

        public Timestamp getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(Timestamp lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public GeneralStatsDto getGeneralStatsDto() {
            return generalStatsDto;
        }

        public void setGeneralStatsDto(GeneralStatsDto generalStatsDto) {
            this.generalStatsDto = generalStatsDto;
        }

        public CategoryStatsDto getCategoryStatsDto() {
            return categoryStatsDto;
        }

        public void setCategoryStatsDto(CategoryStatsDto categoryStatsDto) {
            this.categoryStatsDto = categoryStatsDto;
        }

        public FrequencyStatsDto getFrequencyStatsDto() {
            return frequencyStatsDto;
        }

        public void setFrequencyStatsDto(FrequencyStatsDto frequencyStatsDto) {
            this.frequencyStatsDto = frequencyStatsDto;
        }

        public Last7DaysScoresDto getLast7DaysScoresDto() {
            return last7DaysScoresDto;
        }

        public void setLast7DaysScoresDto(Last7DaysScoresDto last7DaysScoresDto) {
            this.last7DaysScoresDto = last7DaysScoresDto;
        }



    }

