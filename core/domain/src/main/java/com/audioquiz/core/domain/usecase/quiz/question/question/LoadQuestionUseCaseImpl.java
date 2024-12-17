package com.audioquiz.core.domain.usecase.quiz.question.question;


import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.model.quiz.Question;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LoadQuestionUseCaseImpl {
    private static final String TAG = "LoadQuestionUseCaseImpl";
    private final QuizRepository quizRepository;

    private final FetchIntervalQuestionUseCaseImpl fetchIntervalQuestionUseCaseImpl;
    private final FetchPitchQuestionUseCaseImpl fetchPitchQuestionUseCaseImpl;
    private final FetchTextQuestionUseCaseImpl fetchTextQuestionUseCaseImpl;

    @Inject
    public LoadQuestionUseCaseImpl(QuizRepository quizRepository,
                                   FetchIntervalQuestionUseCaseImpl fetchIntervalQuestionUseCaseImpl,
                                   FetchPitchQuestionUseCaseImpl fetchPitchQuestionUseCaseImpl,
                                   FetchTextQuestionUseCaseImpl fetchTextQuestionUseCaseImpl) {
        this.quizRepository = quizRepository;
        this.fetchIntervalQuestionUseCaseImpl = fetchIntervalQuestionUseCaseImpl;
        this.fetchPitchQuestionUseCaseImpl = fetchPitchQuestionUseCaseImpl;
        this.fetchTextQuestionUseCaseImpl = fetchTextQuestionUseCaseImpl;
    }


    public Single <Question> execute () {
        Single <Question>  questionSingle = fetchQuestion();
        if (questionSingle == null) {
            questionSingle = fetchTextQuestion("all", 0);
        }
        return questionSingle;
    }

    private Single <Question> fetchQuestion() {
        String category = getQuestionCategory();
        String quizType = getQuestionType();
        int chapter = getQuestionChapter();

        Single <Question>  question = null;

        if (category != null) {
            switch (category) {
                case "pitch":
                    question = fetchPitchQuestion();
                    break;
                case "intervals":
                    question = fetchIntervalQuestion(quizType);
                    break;
                default:
                    question = fetchTextQuestion(category, chapter);
                    break;
            }
        }
   //     setCurrentQuestionLiveData(question);
        return question;
    }

    private int getQuestionChapter() {
        return quizRepository.getQuizResult().getChapter();
    }

    private String getQuestionType() {
        return quizRepository.getQuizResult().getQuizType();
    }

    private String getQuestionCategory() {
        return quizRepository.getQuizResult().getCategory();
    }


    private Single <Question> fetchIntervalQuestion(String rootNote) {
        return fetchIntervalQuestionUseCaseImpl.execute(rootNote);
    }

    private Single <Question>  fetchPitchQuestion() {
        return fetchPitchQuestionUseCaseImpl.execute();
    }

/*    private Question fetchTextQuestion(String category, int chapter) {
        return fetchTextQuestionUseCaseImpl.execute(category, chapter);
    }*/
    private Single <Question> fetchTextQuestion(String category, int chapter) {
        return fetchTextQuestionUseCaseImpl.execute(category, chapter);
    }

/*    private void setCurrentQuestionLiveData (Question question) {
        questionRepository.setCurrentQuestionLiveData(question);
    }*/
}


