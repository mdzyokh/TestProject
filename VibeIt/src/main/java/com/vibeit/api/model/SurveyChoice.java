package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Maria Dzyokh
 */
public class SurveyChoice implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("survey_id")
    private int surveyId;
    @SerializedName("survey_question_id")
    private int surveyQuestionId;
    @SerializedName("choice")
    private String choice;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(int surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyChoice that = (SurveyChoice) o;

        if (id != that.id) return false;
        if (surveyId != that.surveyId) return false;
        if (surveyQuestionId != that.surveyQuestionId) return false;
        if (choice != null ? !choice.equals(that.choice) : that.choice != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + surveyId;
        result = 31 * result + surveyQuestionId;
        result = 31 * result + (choice != null ? choice.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
