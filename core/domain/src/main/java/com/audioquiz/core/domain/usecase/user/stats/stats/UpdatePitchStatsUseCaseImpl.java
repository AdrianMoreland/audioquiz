package com.audioquiz.core.domain.usecase.user.stats.stats;


import com.audioquiz.core.model.quiz.QuizResult;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.PitchStats;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;


public class UpdatePitchStatsUseCaseImpl {

    @Inject
    public UpdatePitchStatsUseCaseImpl() {

    }

    public FrequencyStats execute(QuizResult quizResult, FrequencyStats frequencyStats) {
        Map<String, Integer> quizPitchScores = quizResult.getScorePerFrequency();
        Map<String, PitchStats> totalPitchScores = frequencyStats.getPitchScoresMap();

        for (Map.Entry<String, Integer> entry : quizPitchScores.entrySet()) {
            String frequency = entry.getKey();
            Integer selectedFrequency = entry.getValue();
            if (!totalPitchScores.containsKey(frequency)) {
                totalPitchScores.put(frequency, PitchStats.createDefault(frequency));
            }
            PitchStats pitchStats = totalPitchScores.get(frequency);
            if (pitchStats != null) {
                int totalQuestions = pitchStats.getTotalQuestions();
                int currentScore = pitchStats.getCorrectAnswers();
                Map<String, Integer> mistakes = pitchStats.getMistakes();

                pitchStats.setFrequency(frequency);
                pitchStats.setTotalQuestions(totalQuestions + 1);

                // If the selected frequency is equal to the frequency number, increment the correct answers
                // Otherwise, increment the mistakes for the selected frequency
                if (Objects.equals(entry.getKey(), entry.getValue().toString())) {
                    pitchStats.setCorrectAnswers(currentScore + 1);
                } else {
                    mistakes.merge(String.valueOf(selectedFrequency), 1, Integer::sum);
                    pitchStats.setMistakes(mistakes);
                }
            }
        }

        return frequencyStats;
    }
}
