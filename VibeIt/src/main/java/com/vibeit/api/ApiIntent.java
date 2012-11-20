package com.vibeit.api;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import com.vibeit.api.action.AbstractAction;

/**
 * @author Maria Dzyokh
 */
public class ApiIntent extends Intent {
	public ApiIntent(Context ctx, ResultReceiver receiver, Class actionClass) {
		super(ctx, ApiService.class);
		setAction(actionClass.getName());
		putExtra(AbstractAction.EXTRA_RECEIVER, receiver);
	}
}
