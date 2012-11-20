package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.UserQuestion;
import com.vibeit.api.model.UserQuestionChoice;

/**
 * @author Andrii Kovalov
 */
public class UserQuestionPayload extends BasicPayload {
	@SerializedName("UserQuestion")
	private UserQuestion question;
	@SerializedName("UserQuestionChoice")
	private UserQuestionChoice[] choices;

	public com.vibeit.api.model.UserQuestion getQuestion() {
		return question;
	}

	public void setQuestion(com.vibeit.api.model.UserQuestion question) {
		this.question = question;
	}

	public UserQuestionChoice[] getChoices() {
		return choices;
	}

	public void setChoices(UserQuestionChoice[] choices) {
		this.choices = choices;
	}
}
