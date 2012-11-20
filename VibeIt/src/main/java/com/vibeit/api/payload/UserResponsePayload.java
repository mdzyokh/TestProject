package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.*;

/**
 * @author Andrii Kovalov
 */
public class UserResponsePayload extends BasicPayload {
	@SerializedName("User")
	private User user;
	@SerializedName("UserToken")
	private UserToken userToken;
	@SerializedName("UserSurveyReward")
	private UserSurveyReward[] userSurveyReward;
	@SerializedName("UserNotification")
	private UserNotification[] userNotification;
	@SerializedName("SurveyReward")
	private SurveyReward[] surveyReward;
	@SerializedName("Survey")
	private Survey[] survey;
	@SerializedName("Location")
	private Location[] location;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}

	public UserSurveyReward[] getUserSurveyReward() {
		return userSurveyReward;
	}

	public void setUserSurveyReward(UserSurveyReward[] userSurveyReward) {
		this.userSurveyReward = userSurveyReward;
	}

	public UserNotification[] getUserNotification() {
		return userNotification;
	}

	public void setUserNotification(UserNotification[] userNotification) {
		this.userNotification = userNotification;
	}

	public SurveyReward[] getSurveyReward() {
		return surveyReward;
	}

	public void setSurveyReward(SurveyReward[] surveyReward) {
		this.surveyReward = surveyReward;
	}

	public Survey[] getSurvey() {
		return survey;
	}

	public void setSurvey(Survey[] survey) {
		this.survey = survey;
	}

	public Location[] getLocation() {
		return location;
	}

	public void setLocation(Location[] location) {
		this.location = location;
	}
}
