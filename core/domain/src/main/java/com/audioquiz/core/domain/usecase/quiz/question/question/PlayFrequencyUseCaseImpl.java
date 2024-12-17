package com.audioquiz.core.domain.usecase.quiz.question.question;

import com.audioquiz.core.domain.service.FrequencyPlayer;
import com.audioquiz.core.model.quiz.Question;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;


public class PlayFrequencyUseCaseImpl {
    private final FrequencyPlayer frequencyPlayer;
    private static final String TAG = "PlayFrequencyUseCaseImpl";

    @Inject
    public PlayFrequencyUseCaseImpl(FrequencyPlayer frequencyPlayer) {
        this.frequencyPlayer = frequencyPlayer;
    }

    public void playPitchFrequency(int frequency) {
        frequencyPlayer.playFrequency(frequency);
    }

    public void playIntervalFrequency(double rootNote, double secondNote) {
        frequencyPlayer.playFrequency(rootNote);
        // Delay the playing of the second note
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                frequencyPlayer.playFrequency(secondNote);
                System.out.println("Playing frequencies: " + rootNote + " and " + secondNote);
            }
        }, 1000); // Delay in milliseconds
    }

    public void playFrequency(Question question) {
        if (question.getCategory().equals("pitch")) {
            // Get the correct frequency from the question
            String correctOption = question.getCorrectOption();
            int correctFrequency = Integer.parseInt(correctOption);
            System.out.println(TAG + "Playing frequency: " + correctFrequency);
            playPitchFrequency(correctFrequency);
        } else {
            double rootNote = Double.parseDouble(question.getOption3());
            double secondNote = Double.parseDouble(question.getOption4());
            playIntervalFrequency(rootNote, secondNote);
        }
    }
}
