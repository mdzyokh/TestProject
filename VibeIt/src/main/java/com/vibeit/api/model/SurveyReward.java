package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class SurveyReward implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("survey_id")
    private int surveyId;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private float value;
    @SerializedName("description")
    private String description;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;
    @SerializedName("is_available")
    private int isAvailabe;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getAvailabe() {
        return isAvailabe;
    }

    public void setAvailabe(int availabe) {
        isAvailabe = availabe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyReward that = (SurveyReward) o;

        if (id != that.id) return false;
        if (isAvailabe != that.isAvailabe) return false;
        if (surveyId != that.surveyId) return false;
        if (Float.compare(that.value, value) != 0) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + surveyId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + isAvailabe;
        return result;
    }
}
