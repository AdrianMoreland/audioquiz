package com.audioquiz.data.util.mapper;


import com.audioquiz.core.model.quiz.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class QuestionJsonMapper {

    @Inject
    public QuestionJsonMapper() {
        // Default constructor
    }

    public static Question mapFromJson(JSONObject jsonObject) throws JSONException {
        String category = jsonObject.getString("category");
        int chapter = jsonObject.getInt("chapter");
        int level = jsonObject.getInt("level");
        String questionType = jsonObject.getString("type");
        String questionText = jsonObject.getString("question");
        int localCorrectAnswer = jsonObject.getInt("correctAnswer");

        String[] options = new String[4];

        if (questionType.equals("yes_no")) {
            options[0] = "Yes";
            options[1] = "No";
            for (int j = 2; j < 4; j++) {
                options[j] = "N/A";
            }
        } else {
            JSONArray optionsArray = jsonObject.getJSONArray("options");
            for (int j = 0; j < optionsArray.length() && j < options.length; j++) {
                options[j] = optionsArray.getString(j);
            }
            for (int j = optionsArray.length(); j < 4; j++) {
                options[j] = "N/A";
            }
            String correctAnswerOption = options[localCorrectAnswer];

            List<String> optionsList = new ArrayList<>(Arrays.asList(options));
            Collections.shuffle(optionsList);
            options = optionsList.toArray(new String[0]);

            localCorrectAnswer = Arrays.asList(options).indexOf(correctAnswerOption);
        }

        Question question = new Question();
        question.setCategory(category);
        question.setChapter(chapter);
        question.setLevel(level);
        question.setType(questionType);
        question.setQuestionText(questionText);
        question.setOption1(options[0]);
        question.setOption2(options[1]);
        question.setOption3(options[2]);
        question.setOption4(options[3]);
        question.setAnswerNr(localCorrectAnswer);
        question.setExplanation(jsonObject.getString("explanation"));

        return question;
    }
}