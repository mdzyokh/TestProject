package com.vibeit.screen;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.vibeit.R;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.action.LoginAction;
import com.vibeit.util.UiUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maria Dzyokh
 */
public class ForgotPasswordScreen extends AbstractActivity {

    private ApiResultReceiver forgotPasswordReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<Boolean> response = (ApiResponse<Boolean>) resultData.getSerializable(LoginAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    onBackPressed();
                } else {
                    Toast.makeText(ForgotPasswordScreen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(ForgotPasswordScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        aq.id(R.id.btn_submit).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    showProgress();
                    VibeItApiUtils.forgotPassword(ForgotPasswordScreen.this, forgotPasswordReceiver, aq.id(R.id.email).getText().toString().trim());
                }
            }
        });

        aq.id(R.id.btn_cancel).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        return valid;
    }
}
