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
public class LoginAction extends PostAbstractAction {
	public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
	public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";

	private final String email;
	private final String password;

	public LoginAction(Intent intent) {
		super(intent);
		this.email = intent.getStringExtra(EXTRA_EMAIL);
		this.password = intent.getStringExtra(EXTRA_PASSWORD);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("email", email);
        requestBody.add("password", password);
        return requestBody;
	}

	@Override
	protected String getUrl() { return "/api/users/login"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserResponsePayload>>() {}.getType();
	}
}
