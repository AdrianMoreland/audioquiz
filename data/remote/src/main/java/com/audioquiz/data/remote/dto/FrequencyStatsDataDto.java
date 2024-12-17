package com.audioquiz.data.remote.dto;

import java.util.Map;

public class FrequencyStatsDataDto {
    public int frequency;
    public int totalQuestions;
    public int correctAnswers;
    public Map<String, Integer> mistakes;

    public FrequencyStatsDataDto() {
    }

}
