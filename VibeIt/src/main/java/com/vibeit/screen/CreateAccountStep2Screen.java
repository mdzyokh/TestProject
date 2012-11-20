package com.vibeit.screen;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.vibeit.R;
import com.vibeit.adapter.UserQuestionChoiceSpinnerAdapter;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.model.UserQuestionChoice;
import com.vibeit.api.payload.UserQuestionPayload;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.model.AccountInfo;
import com.vibeit.util.UiUtils;
import com.vibeit.widget.SwitchICS;

/**
 * @author Maria Dzyokh
 */
public class CreateAccountStep2Screen extends AbstractActivity implements View.OnClickListener {

    private ApiResultReceiver userQuestionsReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<UserQuestionPayload[]> response = (ApiResponse<UserQuestionPayload[]>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (response.isSuccess()) {
                    aq.id(R.id.gender_drop_down).adapter(new UserQuestionChoiceSpinnerAdapter(CreateAccountStep2Screen.this, response.getData()[0].getChoices()));
                    aq.id(R.id.age_drop_down).adapter(new UserQuestionChoiceSpinnerAdapter(CreateAccountStep2Screen.this, response.getData()[1].getChoices()));
                } else {
                    Toast.makeText(CreateAccountStep2Screen.this, response.getError(), Toast.LENGTH_LONG).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(CreateAccountStep2Screen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private ApiResultReceiver userInfoReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<UserResponsePayload> response = (ApiResponse<UserResponsePayload>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (response.isSuccess()) {
                    getApp().saveUserInfo(response.getPayload());
                    IntentHelper.homeScreen(CreateAccountStep2Screen.this);
                } else {
                    Toast.makeText(CreateAccountStep2Screen.this, response.getError(), Toast.LENGTH_LONG).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(CreateAccountStep2Screen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private AccountInfo accountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_step_2);
        UiUtils.initChains(this);

        aq.id(R.id.btn_continue).clicked(this);
        aq.id(R.id.btn_close).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initAccountInfo();
        fetchUserQuestions();
    }

    @Override
    public void onClick(View v) {
        showProgress();
        SwitchICS allowContactBtn = (SwitchICS) aq.id(R.id.allow_contact).getView();
        accountInfo.setAllowContact(!allowContactBtn.isChecked());
        accountInfo.addUserQuestionAnswer((UserQuestionChoice) aq.id(R.id.gender_drop_down).getSelectedItem());
        accountInfo.addUserQuestionAnswer((UserQuestionChoice) aq.id(R.id.age_drop_down).getSelectedItem());
        VibeItApiUtils.createAccount(this, userInfoReceiver, accountInfo);
    }

    private void initAccountInfo() {
        accountInfo = getIntent().getParcelableExtra(CreateAccountScreen.EXTRA_ACCOUNT_INFO);
    }

    private void fetchUserQuestions() {
        showProgress();
        VibeItApiUtils.getUserQuestions(this, userQuestionsReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_slide_down);
    }
}
