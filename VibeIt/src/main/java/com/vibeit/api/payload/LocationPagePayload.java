package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.Location;
import com.vibeit.api.model.Survey;
import com.vibeit.api.model.SurveyReward;

/**
 * @author Maria Dzyokh
 */
public class LocationPagePayload extends BasicPayload {

    @SerializedName("Location")
    protected Location location;
    @SerializedName("SurveyReward")
    protected SurveyReward[] reward;
    protected Survey[] survey;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Survey[] getSurvey() {
        return survey;
    }

    public void setSurvey(Survey[] survey) {
        this.survey = survey;
    }

    public SurveyReward[] getReward() {
        return reward;
    }

    public void setReward(SurveyReward[] reward) {
        this.reward = reward;
    }

    public boolean hasSurveyReward() {
        return getReward()!=null && getReward().length > 0;
    }

    public boolean hasSurvey() {
        return getSurvey()!=null && getSurvey().length > 0;
    }
}
