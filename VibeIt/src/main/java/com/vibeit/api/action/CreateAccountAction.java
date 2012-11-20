package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.model.AccountInfo;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class CreateAccountAction extends PostAbstractAction {
	public static final String EXTRA_ACCOUNT_INFO = "EXTRA_ACCOUNT_INFO";

	private final AccountInfo accountInfo;

	public CreateAccountAction(Intent intent) {
		super(intent);
		accountInfo = intent.getParcelableExtra(EXTRA_ACCOUNT_INFO);
	}

	@Override
	protected MultiValueMap<String, String> getParams() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("first_name", accountInfo.getFirstName());
		params.add("last_name", accountInfo.getLastName());
		params.add("email", accountInfo.getEmail());
		if (accountInfo.hasPassword()) {
			params.add("password", accountInfo.getPassword());
			params.add("confirm", accountInfo.getPasswordConfirm());
		}
		if (accountInfo.hasFbToken()) {
			params.add("access_token", accountInfo.getFbToken());
		}
		params.add("allow_contact", accountInfo.isAllowContact() ? "1" : "0");
        params.add("questions", accountInfo.composeQuestionAnswersJson());
		return params;
	}

	@Override
	protected String getUrl() { return "/api/users/register"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<UserResponsePayload>>() {}.getType();
	}

}
