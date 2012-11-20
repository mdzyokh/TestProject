package com.vibeit.api.action;

import android.content.Intent;
import android.util.Log;

/**
 * @author Andrii Kovalov
 */
public class ActionFactory {
	private static final String TAG = ActionFactory.class.getSimpleName();

	public static AbstractAction create(Intent intent) {
		try {
			return (AbstractAction) Class.forName(intent.getAction()).getConstructor(Intent.class).newInstance(intent);
		} catch (Exception e) {
			String message = e.getMessage() != null ? e.getMessage() : "";
			Log.d(TAG, message, e);
			Log.e(TAG, message);
			return null;
		}
	}
}
