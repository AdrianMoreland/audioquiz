package com.audioquiz.data.remote.dto;

import com.google.firebase.Timestamp;

import java.util.Date;

public class UserStatsDto extends BaseUserStats {
    public Date lastUpdated;
    public GeneralStatsDto generalStats;
    public CategoryStatsDto categoryStats;
    public FrequencyStatsDto frequencyStats;
    public Last7DaysScoresDto last7DaysScores;

    public UserStatsDto() {
        super();
    }

    public UserStatsDto(Id id,
                        Timestamp lastUpdated,
                        GeneralStatsDto generalStatsDto,
                        CategoryStatsDto categoryStatsDto,
                        FrequencyStatsDto frequencyStatsDto,
                        Last7DaysScoresDto last7DaysScoresDto) {
        super(id, lastUpdated, generalStatsDto, categoryStatsDto, frequencyStatsDto, last7DaysScoresDto);
    }

}

