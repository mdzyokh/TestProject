package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author MariaDzyokh
 */
public class ForgotPasswordAction extends PostAbstractAction {
	public static final String EXTRA_EMAIL = "EXTRA_EMAIL";

	private final String email;

	public ForgotPasswordAction(Intent intent) {
		super(intent);
		this.email = intent.getStringExtra(EXTRA_EMAIL);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("email", email);
		return params;
	}

	@Override
	protected String getUrl() { return "/api/users/forgotPassword"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<Boolean>>() {}.getType();
	}
}
