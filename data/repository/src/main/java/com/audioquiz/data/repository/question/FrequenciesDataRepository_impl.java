package com.audioquiz.data.repository.question;


import android.util.Log;

import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;
import com.audioquiz.data.util.MusicData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class FrequenciesDataRepository_impl implements FrequenciesDataRepository {
    private static final String TAG = "FrequenciesDataRepository_impl";
    private final Random random = new Random();
    private List<Integer> frequenciesList = new ArrayList<>();
    private List<Integer> intervalKeysList = new ArrayList<>();
    private Map<Integer, String> intervalButtonsIndex = new HashMap<>();
    private Map<String, List<String>> intervalQualitiesIndex = new HashMap<>();
    public Map<Integer, List<String>> intervalSemitonesList = new HashMap<>();
    public Map<String, Double> noteFrequenciesList = new HashMap<>();

    @Inject
    public FrequenciesDataRepository_impl() {
        init();
    }

    public void init() {
        frequenciesList = MusicData.FREQUENCIES;
        intervalKeysList =  new ArrayList<>(MusicData.INTERVAL_SEMITONES.keySet());
        intervalButtonsIndex = MusicData.INTERVAL_BUTTONS;
        intervalQualitiesIndex = MusicData.INTERVAL_QUALITIES;
        intervalSemitonesList = MusicData.INTERVAL_SEMITONES;
        noteFrequenciesList = MusicData.NOTE_FREQUENCIES;
    }

    @Override
    public Integer fetchRandomIntervalNumber() {
        Integer intervalKey = intervalKeysList.get(random.nextInt(intervalKeysList.size()));
        Log.d(TAG, "fetchRandomIntervalNumber: " + intervalKey);
        return intervalKey;
    }

    @Override
    public String fetchRandomIntervalQuality(Integer randomIntervalNumber) {
        List<String> intervalQualities = intervalSemitonesList.get(randomIntervalNumber);
        if (intervalQualities == null || intervalQualities.isEmpty()) {
            throw new IllegalArgumentException("Invalid interval number: " + randomIntervalNumber);
        }
        String randomQuality = intervalQualities.get(random.nextInt(intervalQualities.size()));
        Log.d(TAG, "fetchRandomIntervalQuality: " + randomQuality);
        return randomQuality;
    }

    @Override
    public Map<String, List<String>> getIntervalQualitiesIndex() {
        Log.d(TAG, "getIntervalQualitiesIndex: " + intervalQualitiesIndex);
        return intervalQualitiesIndex;
    }

    @Override
    public Double fetchFrequency(String note) {
        Log.d(TAG, "fetchFrequency: " + noteFrequenciesList.get(note));
        return noteFrequenciesList.get(note);
    }

    @Override
    public List<Integer> getFrequenciesList() {
        Log.d(TAG, "getFrequenciesList: " + frequenciesList);
        return frequenciesList;
    }

    @Override
    public Map<Integer, String> getIntervalButtonsIndex() {
        Log.d(TAG, "getIntervalButtonsIndex: " + intervalButtonsIndex);
        return intervalButtonsIndex;
    }

    @Override
    public String determineSecondNoteOfInterval(String rootNote, Integer randomIntervalNumber) {
        List<String> noteKeys = new ArrayList<>(MusicData.NOTE_FREQUENCIES.keySet());
        int rootNoteIndex = noteKeys.indexOf(rootNote);
        int secondNoteIndex = (rootNoteIndex + randomIntervalNumber) % noteKeys.size();
        Log.d(TAG, "determineSecondNoteOfInterval: " + noteKeys.get(secondNoteIndex));
        return noteKeys.get(secondNoteIndex);
    }
}


