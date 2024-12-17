package com.audioquiz.core.domain.usecase.quiz.question;

import java.util.List;
import java.util.Map;

public interface FrequenciesDataUseCase {
    Map<Integer, String> getIntervalButtons();

    Map<String, List<String>> getIntervalQualities();
}
