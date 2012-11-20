package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.Survey;
import com.vibeit.api.model.SurveyChoice;
import com.vibeit.api.model.SurveyQuestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maria Dzyokh
 */
public class SurveyPayload extends BasicPayload {

    @SerializedName("Survey")
    private Survey survey;
    @SerializedName("SurveyQuestion")
    private SurveyQuestion[] questions;
    @SerializedName("SurveyChoice")
    private SurveyChoice[] choices;

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public SurveyQuestion[] getQuestions() {
        return questions;
    }

    public void setQuestions(SurveyQuestion[] questions) {
        this.questions = questions;
    }

    public SurveyChoice[] getChoices() {
        return choices;
    }

    public void setChoices(SurveyChoice[] choices) {
        this.choices = choices;
    }

    public List<SurveyChoice> getQuestionChoices(int questionId) {
        List<SurveyChoice> surveyChoices = new ArrayList<SurveyChoice>();
        for (SurveyChoice item : choices) {
            if (item.getSurveyQuestionId() == questionId) {
                surveyChoices.add(item);
            }
        }
        return surveyChoices;
    }
}
