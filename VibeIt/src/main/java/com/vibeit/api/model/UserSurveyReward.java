package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class UserSurveyReward implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("location_id")
    private int locationId;
    @SerializedName("survey_reward_id")
    private int surveyRewardId;
    @SerializedName("code")
    private String code;
    @SerializedName("claimed")
    private String claimed;
    @SerializedName("deleted")
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getSurveyRewardId() {
        return surveyRewardId;
    }

    public void setSurveyRewardId(int surveyRewardId) {
        this.surveyRewardId = surveyRewardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClaimed() {
        return claimed;
    }

    public void setClaimed(String claimed) {
        this.claimed = claimed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSurveyReward that = (UserSurveyReward) o;

        if (deleted != that.deleted) return false;
        if (id != that.id) return false;
        if (locationId != that.locationId) return false;
        if (surveyRewardId != that.surveyRewardId) return false;
        if (userId != that.userId) return false;
        if (claimed != null ? !claimed.equals(that.claimed) : that.claimed != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + locationId;
        result = 31 * result + surveyRewardId;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (claimed != null ? claimed.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }
}
