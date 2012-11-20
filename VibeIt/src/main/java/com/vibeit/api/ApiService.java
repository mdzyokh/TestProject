package com.vibeit.api;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.action.ActionFactory;

/**
 * @author Andrii Kovalov
 */
public class ApiService extends IntentService {
	private static final String NAME = ApiService.class.getSimpleName();

	public ApiService() {
		super(NAME);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			AbstractAction action = ActionFactory.create(intent);
			if (action != null) {
				Log.d(NAME, "Execute: " + intent.getAction());
				action.execute();
			}
		}
	}
}
