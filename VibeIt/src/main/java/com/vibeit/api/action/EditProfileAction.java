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
public class EditProfileAction extends PostAbstractAction {
	public static final String EXTRA_FIRST_NAME = "EXTRA_FIRST_NAME";
	public static final String EXTRA_LAST_NAME = "EXTRA_LAST_NAME";
	public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
	public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
	public static final String EXTRA_CONFIRM_PASSWORD = "EXTRA_CONFIRM_PASSWORD";
	public static final String EXTRA_ALLOW_CONTACT = "EXTRA_ALLOW_CONTACT";
	public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";

	private final String firstName;
	private final String lastName;
	private final String email;
	private final String password;
	private final String confirmPassword;
	private final String userToken;
	private final boolean allowContact;

	public EditProfileAction(Intent intent) {
		super(intent);
		this.firstName = intent.getStringExtra(EXTRA_FIRST_NAME);
		this.lastName = intent.getStringExtra(EXTRA_LAST_NAME);
		this.email = intent.getStringExtra(EXTRA_EMAIL);
		this.password = intent.getStringExtra(EXTRA_PASSWORD);
		this.confirmPassword = intent.getStringExtra(EXTRA_CONFIRM_PASSWORD);
		this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
		this.allowContact = intent.getBooleanExtra(EXTRA_ALLOW_CONTACT, false);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("first_name", firstName);
		requestBody.add("last_name", lastName);
		requestBody.add("email", email);
		requestBody.add("password", password);
		requestBody.add("confirm", confirmPassword);
		requestBody.add("allow_contact", allowContact ? "1" : "0");
		requestBody.add("user_token", userToken);
		return requestBody;
	}

	@Override
	protected String getUrl() { return "/api/users/edit"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserResponsePayload>>() {}.getType();
	}
}
