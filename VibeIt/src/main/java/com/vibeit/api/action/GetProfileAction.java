package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.UserResponsePayload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class GetProfileAction extends PostAbstractAction {
	public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";

	private final String userToken;

	public GetProfileAction(Intent intent) {
		super(intent);
		this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("user_token", userToken);
		return requestBody;
	}

	@Override
	protected String getUrl() { return "/api/users/profile"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserResponsePayload>>() {}.getType();
	}
}
