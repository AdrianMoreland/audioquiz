package com.audioquiz.core.domain.usecase.quiz.question.question;


import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.model.quiz.Question;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class FetchTextQuestionUseCaseImpl {
   private final QuestionRepository questionRepository;
   private final ExecutorService executorService;

    @Inject
    public FetchTextQuestionUseCaseImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.executorService = Executors.newCachedThreadPool(); // Using CachedThreadPool for dynamic thread management
    }

    public Single<Question> execute(String category, int chapter) {
        return questionRepository.getQuestionSingle(category, chapter);
    }


    public Question execute2(String category, int chapter) {
        AtomicReference<Question> questionRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1); // Initialize latch with count 1

        executorService.execute(() -> {
            try {
                Question question = questionRepository.getQuestion(category, chapter);
                questionRef.set(question);
            } finally {
                latch.countDown(); // Decrease latch count when task completes
            }
        });

        try {
            latch.await(); // Wait until latch count becomes zero
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status if necessary
        }

        return questionRef.get();
    }

}
