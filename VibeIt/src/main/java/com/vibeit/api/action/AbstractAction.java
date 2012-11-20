package com.vibeit.api.action;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.vibeit.api.ApiResponse;

import java.lang.reflect.Type;

/**
 * @author Andrii Kovalov
 */
public abstract class AbstractAction<R extends ApiResponse> extends AsyncTask<Object, Object, R> {
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_FAILURE = 1;
	public static final int STATUS_PROGRESS = 2;

	public static final String EXTRA_RECEIVER = "EXTRA_RECEIVER";
	public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
	public static final String EXTRA_ERROR_MESSAGE = "EXTRA_ERROR_MESSAGE";
	public static final String EXTRA_PROGRESS = "EXTRA_PROGRESS";

	protected static final String TAG = AbstractAction.class.getSimpleName();

	private ResultReceiver resultReceiver;

	protected abstract String getUrl();

	protected abstract Type getType();

	protected abstract void doInBackground();

	public AbstractAction(Intent intent) {
		resultReceiver = (ResultReceiver) intent.getParcelableExtra(AbstractAction.EXTRA_RECEIVER);
	}

	@Override
	protected R doInBackground(Object... params) {
		doInBackground();
		return null;
	}

	protected void success(R response) {
		if (hasResultReceiver()) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(EXTRA_RESPONSE, response);
			getResultReceiver().send(STATUS_SUCCESS, bundle);
		}
	}

	protected void failure(String message) {
		if (hasResultReceiver()) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(EXTRA_ERROR_MESSAGE, message);
			getResultReceiver().send(STATUS_FAILURE, bundle);
		}
	}

	public void setResultReceiver(ResultReceiver resultReceiver) {
		this.resultReceiver = resultReceiver;
	}

	protected ResultReceiver getResultReceiver() {
		return resultReceiver;
	}

	protected boolean hasResultReceiver() {
		return resultReceiver != null;
	}

	// TODO: move to settings
	protected String getHost() {
		return "http://vibeit.orainteractive.com";
	}
}