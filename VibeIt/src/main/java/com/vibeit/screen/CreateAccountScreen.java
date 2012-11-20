package com.vibeit.screen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.vibeit.R;
import com.vibeit.intent.IntentHelper;
import com.vibeit.model.AccountInfo;
import com.vibeit.util.UiUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrii Kovalov
 */
public class CreateAccountScreen extends AbstractActivity implements View.OnClickListener {
	public static final String EXTRA_ACCOUNT_INFO = "EXTRA_ACCOUNT_INFO";
	private AccountInfo accountInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		UiUtils.initChains(this);
		aq.id(R.id.btn_continue).clicked(this);
        aq.id(R.id.btn_close).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
		initAccountInfo();
	}

	private void initAccountInfo() {
		accountInfo = getIntent().getParcelableExtra(EXTRA_ACCOUNT_INFO);

		if (accountInfo != null) {
			aq.id(R.id.password).gone();
			aq.id(R.id.password_confirm).gone();
			aq.id(R.id.first_name).text(accountInfo.getFirstName());
			aq.id(R.id.last_name).text(accountInfo.getLastName());
			aq.id(R.id.email).text(accountInfo.getEmail());
			validateInput();
		} else {
			accountInfo = new AccountInfo();
		}
	}

	private boolean validateInput() {
		boolean valid = true;
		String email = aq.id(R.id.email).getText().toString();
		if (TextUtils.isEmpty(email)) {
			aq.id(R.id.email).getEditText().setError(getString(R.string.email_required));
			valid = false;
		} else {
			Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				aq.id(R.id.email).getEditText().setError(getString(R.string.wrong_email_format));
				valid = false;
			}
		}
		if (TextUtils.isEmpty(aq.id(R.id.first_name).getText())) {
			aq.id(R.id.first_name).getEditText().setError(getString(R.string.first_name_required));
			valid = false;
		}
		if (TextUtils.isEmpty(aq.id(R.id.last_name).getText())) {
			aq.id(R.id.last_name).getEditText().setError(getString(R.string.last_name_field_hint));
			valid = false;
		}
		return valid;
	}

	@Override
	public void onClick(View v) {
		if (validateInput()) {
			accountInfo.setEmail(aq.id(R.id.email).getText().toString());
			accountInfo.setFirstName(aq.id(R.id.first_name).getText().toString());
			accountInfo.setLastName(aq.id(R.id.last_name).getText().toString());
            accountInfo.setPassword(aq.id(R.id.password).getText().toString());
            accountInfo.setPasswordConfirm(aq.id(R.id.password_confirm).getText().toString());
			IntentHelper.createAccountStep2(this, accountInfo);
            overridePendingTransition(R.anim.activity_slide_up, 0);
        }
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_slide_down);
    }
}