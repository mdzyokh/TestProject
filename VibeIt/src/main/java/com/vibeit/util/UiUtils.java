package com.vibeit.util;

import android.app.Activity;
import com.vibeit.R;
import com.vibeit.drawable.DefaultBackground;
import com.vibeit.drawable.VerticalDrawable;

/**
 * @author Andrii Kovalov
 */
public class UiUtils {

	public static void initChains(Activity activity) {
		activity.findViewById(R.id.left_chain).setBackgroundDrawable(new VerticalDrawable(activity.getResources(), R.drawable.vertical_chain_pattern));
		activity.findViewById(R.id.right_chain).setBackgroundDrawable(new VerticalDrawable(activity.getResources(), R.drawable.vertical_chain_pattern));
	}

	public static void setDefaultBackground(Activity activity) {
		activity.getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundDrawable(DefaultBackground.create(activity));
	}
}
