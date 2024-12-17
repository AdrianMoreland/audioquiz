package com.audioquiz.core.domain.usecase.quiz.question.question;


import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;
import com.audioquiz.core.model.quiz.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class FetchPitchQuestionUseCaseImpl {
    private final FrequenciesDataRepository frequenciesDataRepository;
    private static final String TAG = "FetchIntervalQuestionUseCaseImpl";
    private final Random random = new Random();

    @Inject
    public FetchPitchQuestionUseCaseImpl(FrequenciesDataRepository frequenciesDataRepository) {
        this.frequenciesDataRepository = frequenciesDataRepository;
    }


    public Single<Question> execute() {
        List<Integer> frequenciesList = frequenciesDataRepository.getFrequenciesList();
        int correctFrequency = frequenciesList.get(random.nextInt(frequenciesList.size()));
        List<Integer> options = new ArrayList<>();
        options.add(correctFrequency);

        while (options.size() < 4) {
            int freq = frequenciesList.get(random.nextInt(frequenciesList.size()));
            if (!options.contains(freq)) {
                options.add(freq);
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setOption1(String.valueOf(options.get(0)));
        question.setOption2(String.valueOf(options.get(1)));
        question.setOption3(String.valueOf(options.get(2)));
        question.setOption4(String.valueOf(options.get(3)));
        question.setAnswerNr(options.indexOf(correctFrequency));
        question.setId(1);
        question.setCategory("pitch");
        question.setChapter(1);
        question.setLevel(1);
        question.setType("play");
        question.setQuestionText(String.valueOf(correctFrequency));
        question.setExplanation("The correct frequency is " + correctFrequency + " Hz.");

        //   Log.d(TAG, "QuestionDto: " + question.getOption1() + " " + question.getOption2() + " " + question.getOption3() + " " + question.getOption4() + " " + question.getAnswerNr() + " " + question.getExplanation());

        return Single.just(question);
    }
}
