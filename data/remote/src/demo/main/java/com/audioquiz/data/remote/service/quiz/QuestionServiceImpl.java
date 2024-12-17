package com.audioquiz.data.remote.service.quiz;

import com.audioquiz.api.datasources.question.QuestionApi;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.QuestionDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QuestionServiceImpl implements QuestionApi {
    FirestoreDataSource<QuestionDto> dataSource;

    @Inject
    public QuestionServiceImpl(FirestoreDataSource<QuestionDto> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Question> getQuestions() {
        List<QuestionDto> questionDtos = dataSource.getItems("questions", QuestionDto.class);
        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : questionDtos) {
            questions.add(mapToDomain(questionDto));
        }
        return questions;
    }

    @Override
    public Question getQuestion() {
        return null;
    }

    @Override
    public Question getQuestionByCategory(String category) {
        return null;
    }

    @Override
    public Question getQuestionByCategoryAndChapter(String category, int chapter) {
        return null;
    }

    @Override
    public Question getQuestionById(int id) {
        return null;
    }

    @Override
    public void insert(Question questionEntity) {

    }

    // MAPPER
    private Question mapToDomain(QuestionDto questionDto) {
        return NetworkMapper.map(questionDto, Question.class);
    }

    private QuestionDto mapFromDomain(Question question) {
        return NetworkMapper.map(question, QuestionDto.class);
    }
}
