package com.audioquiz.core.domain.repository.quiz;

import java.util.List;
import java.util.Map;

public interface FrequenciesDataRepository {
    void init();
    List<Integer> getFrequenciesList();
    Map<Integer, String> getIntervalButtonsIndex();
    Integer fetchRandomIntervalNumber();
    String fetchRandomIntervalQuality(Integer randomIntervalNumber);
    Map<String, List<String>> getIntervalQualitiesIndex();
    String determineSecondNoteOfInterval(String rootNote, Integer randomIntervalNumber);
    Double fetchFrequency(String rootNote);
}
