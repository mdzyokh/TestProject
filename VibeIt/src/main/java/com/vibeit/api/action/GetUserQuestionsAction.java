package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.UserQuestionPayload;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class GetUserQuestionsAction extends GetAbstractAction {

	public GetUserQuestionsAction(Intent intent) {
		super(intent);
	}

	@Override
	protected MultiValueMap<String, String> getParams() { return null;}

	@Override
	protected String getUrl() {return "/api/users/userQuestions";}

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserQuestionPayload[]>>() {}.getType();
	}
}
