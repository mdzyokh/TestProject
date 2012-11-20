package com.vibeit.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.auth.TwitterHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.vibeit.R;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.model.AccountInfo;
import com.vibeit.util.Settings;
import com.vibeit.util.UiUtils;
import org.json.JSONObject;

/**
 * @author Maria Dzyokh
 */
public class SignInScreen extends AbstractActivity implements View.OnClickListener, ApiResultReceiver.Receiver {
	private static final String TAG = SignInScreen.class.getSimpleName();
	private static final int ACTIVITY_SSO = 1;

	private AQuery aq;
	private FacebookHandle handle;

	private ApiResultReceiver receiver;

    private AccountInfo accountInfo;

	AjaxCallback<JSONObject> fbProfileCallback = new AjaxCallback<JSONObject>() {
		@Override
		public void callback(String url, JSONObject json, AjaxStatus status) {
			if (json != null) {
				// success
				accountInfo = AccountInfo.parse(json);
				accountInfo.setFbToken(handle.getToken());
                //try to login
                SignInScreen.this.showProgress();
                VibeItApiUtils.signInWithFacebook(SignInScreen.this, receiver, handle.getToken());
			} else {
				// failure
				Toast.makeText(SignInScreen.this, "Failed to get user profile", Toast.LENGTH_SHORT).show();
			}
		}
	};

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in_screen);
		aq = new AQuery(this);
		handle = new FacebookHandle(this, getString(R.string.facebook_app_id), "publish_stream, email");
		handle.sso(ACTIVITY_SSO);
		receiver = new ApiResultReceiver(new Handler(), this);
		initButtons();
		AQUtility.setDebug(true);
	}

	private void initButtons() {
		aq.id(R.id.btn_sign_in_email).clicked(this);
		aq.id(R.id.btn_create_account).clicked(this);
		aq.id(R.id.btn_sign_in_facebook).clicked(this);
	}

	public void facebookLogin() {
			handle.ajaxProfile(fbProfileCallback);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case ACTIVITY_SSO: {
				if (handle != null) {
					handle.onActivityResult(requestCode, resultCode, data);
				}
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_sign_in_email:
				IntentHelper.signInWithEmail(SignInScreen.this);
                overridePendingTransition(R.anim.activity_slide_up, 0);
				break;
			case R.id.btn_create_account:
				IntentHelper.createAccount(SignInScreen.this);
                overridePendingTransition(R.anim.activity_slide_up, 0);
				break;
			case R.id.btn_sign_in_facebook:
				facebookLogin();
				break;
		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
        hideProgress();
		if (AbstractAction.STATUS_SUCCESS == resultCode) {
			ApiResponse<UserResponsePayload> response = (ApiResponse<UserResponsePayload>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
			if (!response.isSuccess()) {
				if (!response.hasError()) {
					IntentHelper.createFacebookAccount(SignInScreen.this, accountInfo);
				} else {
					if (response.getError().contains("Error validating access token")) {
						// user removed this app in facebook "My applications section"
						handle.reauth(new AjaxCallback<String>() {
							@Override
							public void callback(String url, String object, AjaxStatus status) {
								if (handle.authenticated()) {
									VibeItApiUtils.signInWithFacebook(SignInScreen.this, receiver, handle.getToken());
								} else {
									Toast.makeText(SignInScreen.this, "Failed to login", Toast.LENGTH_SHORT).show();
								}
							}
						});
					} else {
						Toast.makeText(this, response.getError(), Toast.LENGTH_LONG).show();
					}
				}
			} else {
                getApp().saveUserInfo(response.getPayload());
                IntentHelper.homeScreen(SignInScreen.this);
			}
		}
        if (AbstractAction.STATUS_FAILURE == resultCode) {
            Toast.makeText(SignInScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
        }
	}
}