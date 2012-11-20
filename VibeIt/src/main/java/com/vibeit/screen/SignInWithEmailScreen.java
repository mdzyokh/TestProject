package com.vibeit.screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.vibeit.R;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.action.LoginAction;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.util.UiUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maria Dzyokh
 */
public class SignInWithEmailScreen extends AbstractActivity {

    private String email;

    private ApiResultReceiver userDataReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<UserResponsePayload> response = (ApiResponse<UserResponsePayload>) resultData.getSerializable(LoginAction.EXTRA_RESPONSE);
                if (response.isSuccess()) {
                    getApp().saveUserInfo(response.getPayload());
                    IntentHelper.homeScreen(SignInWithEmailScreen.this);
                } else {
                    Toast.makeText(SignInWithEmailScreen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(SignInWithEmailScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    protected void initViews() {

        UiUtils.initChains(this);

        aq.id(R.id.btn_sign_in_with_email).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    showProgress();
                    VibeItApiUtils.sighInWithEmail(SignInWithEmailScreen.this, userDataReceiver, aq.id(R.id.email).getText().toString(), aq.id(R.id.password).getText().toString());
                }
            }
        });

        aq.id(R.id.btn_forgot_password).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.forgotPasswordScreen(SignInWithEmailScreen.this);
                overridePendingTransition(R.anim.activity_slide_up, 0);
            }
        });

        aq.id(R.id.btn_close).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_with_email);
        initViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_slide_down);
    }

    private boolean validateInput() {
        boolean valid = true;
        if (TextUtils.isEmpty(aq.id(R.id.email).getText())) {
            aq.id(R.id.email).getEditText().setError(getString(R.string.email_required));
            valid = false;
        } else {
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher matcher = pattern.matcher(aq.id(R.id.email).getText().toString());
            if (!matcher.matches()) {
                aq.id(R.id.email).getEditText().setError(getString(R.string.wrong_email_format));
                valid = false;
            }
        }
        if (TextUtils.isEmpty(aq.id(R.id.password).getText())) {
            aq.id(R.id.password).getEditText().setError(getString(R.string.password_required));
            valid = false;
        }
        return valid;
    }
}
