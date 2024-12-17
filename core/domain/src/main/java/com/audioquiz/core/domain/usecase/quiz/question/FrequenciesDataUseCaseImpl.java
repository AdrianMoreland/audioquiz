package com.audioquiz.core.domain.usecase.quiz.question;

import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class FrequenciesDataUseCaseImpl implements FrequenciesDataUseCase {
    private final FrequenciesDataRepository frequenciesDataRepository;


    @Inject
    public FrequenciesDataUseCaseImpl(FrequenciesDataRepository frequenciesDataRepository) {
        this.frequenciesDataRepository = frequenciesDataRepository;
    }


    @Override
    public Map<Integer, String> getIntervalButtons() {
        return frequenciesDataRepository.getIntervalButtonsIndex();
    }

    @Override
    public Map<String, List<String>> getIntervalQualities() {
        return frequenciesDataRepository.getIntervalQualitiesIndex();
    }
}
