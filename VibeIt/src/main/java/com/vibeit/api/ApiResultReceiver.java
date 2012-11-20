package com.vibeit.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * @author Maria Dzyokh
 */
public class ApiResultReceiver extends ResultReceiver {

    public static final String EXTRA_RESULT_RECEIVER = "EXTRA_RESULT_RECEIVER";

    private Receiver receiver;

    public ApiResultReceiver(Handler handler) {
        super(handler);
    }

	public ApiResultReceiver(Handler handler, Receiver receiver) {
		super(handler);
		this.receiver = receiver;
	}

	public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}