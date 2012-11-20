package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class Survey implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("status")
    private int status;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("start")
    private String start;
    @SerializedName("end")
    private String end;
    @SerializedName("limit")
    private int limit;
    @SerializedName("rewards_limit")
    private int rewardsLimit;
    @SerializedName("user_surveys_completed")
    private int userSurveysCompleted;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRewardsLimit() {
        return rewardsLimit;
    }

    public void setRewardsLimit(int rewardsLimit) {
        this.rewardsLimit = rewardsLimit;
    }

    public int getUserSurveysCompleted() {
        return userSurveysCompleted;
    }

    public void setUserSurveysCompleted(int userSurveysCompleted) {
        this.userSurveysCompleted = userSurveysCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Survey survey = (Survey) o;

        if (id != survey.id) return false;
        if (limit != survey.limit) return false;
        if (rewardsLimit != survey.rewardsLimit) return false;
        if (status != survey.status) return false;
        if (userSurveysCompleted != survey.userSurveysCompleted) return false;
        if (description != null ? !description.equals(survey.description) : survey.description != null) return false;
        if (end != null ? !end.equals(survey.end) : survey.end != null) return false;
        if (name != null ? !name.equals(survey.name) : survey.name != null) return false;
        if (start != null ? !start.equals(survey.start) : survey.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + status;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + limit;
        result = 31 * result + rewardsLimit;
        result = 31 * result + userSurveysCompleted;
        return result;
    }
}
