package com.audioquiz.core.domain.usecase.quiz.question.question;


import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;
import com.audioquiz.core.model.quiz.Question;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class FetchIntervalQuestionUseCaseImpl {
    private final FrequenciesDataRepository frequenciesDataRepository;

    private static final String TAG = "FetchIntervalQuestionUseCaseImpl";

    @Inject
    public FetchIntervalQuestionUseCaseImpl(FrequenciesDataRepository frequenciesDataRepository) {
        this.frequenciesDataRepository = frequenciesDataRepository;
    }
    public Single<Question> execute(String rootNote) {
        Integer randomIntervalNumber = frequenciesDataRepository.fetchRandomIntervalNumber();
        String randomIntervalQuality = frequenciesDataRepository.fetchRandomIntervalQuality(randomIntervalNumber);
        String[] parts = splitIntervalQuality(randomIntervalQuality);
        String intervalQuality = parts[0];
        String intervalNum = parts.length > 1 ? parts[1] : "";
        String secondNote = frequenciesDataRepository.determineSecondNoteOfInterval(rootNote, randomIntervalNumber);
        Double rootNoteFrequency = frequenciesDataRepository.fetchFrequency(rootNote);
        Double secondNoteFrequency = frequenciesDataRepository.fetchFrequency(secondNote);
        String questionText = createQuestionText(intervalQuality, intervalNum, rootNote);
        String correctInterval = createCorrectInterval(rootNote, intervalQuality, intervalNum);

        Question question = createQuestion(intervalQuality, intervalNum, rootNoteFrequency, secondNoteFrequency, questionText, correctInterval, randomIntervalNumber);

        return Single.just(question);
    }

    private String[] splitIntervalQuality(String randomIntervalQuality) {
        return randomIntervalQuality.split(" ");
    }

    private String createQuestionText(String intervalQuality, String intervalNum, String rootNote) {
        return "What is the " + intervalQuality + " " + intervalNum + " of " + rootNote + "?";
    }

    private String createCorrectInterval(String rootNote, String intervalQuality, String intervalNum) {
        return rootNote + " " + intervalQuality + " " + intervalNum;
    }

    private Question createQuestion(String intervalQuality, String intervalNum, Double rootNoteFrequency,
                                    Double secondNoteFrequency, String questionText,
                                    String correctInterval, Integer randomIntervalNumber) {
        Question question = new Question();
        question.setOption1(intervalQuality);
        question.setOption2(intervalNum);
        question.setOption3(Objects.requireNonNull(rootNoteFrequency).toString());
        question.setOption4(Objects.requireNonNull(secondNoteFrequency).toString());
        question.setAnswerNr(0);
        question.setId(1);
        question.setCategory("intervals");
        question.setChapter(1);
        question.setLevel(1);
        question.setType("play");
        question.setQuestionText(questionText);
        question.setExplanation("The correct interval is: " + correctInterval + ", at " + randomIntervalNumber + " semitones above the root.");
        return question;
    }


    /*   private Integer fetchRandomIntervalNumber() {
        List<Integer> intervalKeys = new ArrayList<>(MusicData.INTERVAL_SEMITONES.keySet());
        return intervalKeys.get(random.nextInt(intervalKeys.size()));
    }

    private String fetchRandomIntervalQuality(Integer randomIntervalNumber) {
        List<String> intervalQualities = MusicData.INTERVAL_SEMITONES.get(randomIntervalNumber);
        if (intervalQualities == null || intervalQualities.isEmpty()) {
            throw new IllegalArgumentException("Invalid interval number: " + randomIntervalNumber);
        }
        return intervalQualities.get(random.nextInt(intervalQualities.size()));
    }

    private String determineSecondNoteOfInterval(String rootNote, Integer randomIntervalNumber) {
        List<String> noteKeys = new ArrayList<>(MusicData.NOTE_FREQUENCIES.keySet());
        int rootNoteIndex = noteKeys.indexOf(rootNote);
        int secondNoteIndex = (rootNoteIndex + randomIntervalNumber) % noteKeys.size();
        return noteKeys.get(secondNoteIndex);
    }

    private Double fetchFrequency(String note) {
        return MusicData.NOTE_FREQUENCIES.get(note);
    }

 private String[] splitIntervalQuality(String randomIntervalQuality) {
        return randomIntervalQuality.split(" ");
    }

    private String createQuestionText(String intervalQuality, String intervalNum, String rootNote) {
        return "What is the " + intervalQuality + " " + intervalNum + " of " + rootNote + "?";
    }

    private String createCorrectInterval(String rootNote, String intervalQuality, String intervalNum) {
        return rootNote + " " + intervalQuality + " " + intervalNum;
    }

    private QuestionDto createQuestion(String intervalQuality, String intervalNum, Double rootNoteFrequency, Double secondNoteFrequency, String questionText, String correctInterval, Integer randomIntervalNumber) {
        QuestionDto question = new QuestionDto();
        question.setOption1(intervalQuality);
        question.setOption2(intervalNum);
        question.setOption3(Objects.requireNonNull(rootNoteFrequency).toString());
        question.setOption4(Objects.requireNonNull(secondNoteFrequency).toString());
        question.setAnswerNr(0);
        question.setId(1);
        question.setCategory("intervals");
        question.setChapter(1);
        question.setLevel(1);
        question.setType("play");
        question.setQuestionText(questionText);
        question.setExplanation("The correct interval is: " + correctInterval + ", at "+ randomIntervalNumber + " semitones above the root.");
        return question;
    }
*/}
