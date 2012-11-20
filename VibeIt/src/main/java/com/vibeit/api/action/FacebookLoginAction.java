package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.UserResponsePayload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Andrii Kovalov
 */
public class FacebookLoginAction extends PostAbstractAction {
	public static final String EXTRA_TOKEN = "EXTRA_TOKEN";

	private final String accessToken;

	public FacebookLoginAction(Intent intent) {
		super(intent);
		this.accessToken = intent.getStringExtra(EXTRA_TOKEN);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("access_token", accessToken);
		return requestBody;
	}

	@Override
	protected String getUrl() { return "/api/users/facebookLogin"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserResponsePayload>>() {}.getType();
	}
}