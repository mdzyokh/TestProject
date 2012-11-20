package com.cyberwalkabout.common.util;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

/**
 * @author Andrii Kovalov
 */
public class UiUtils
{
	public static void expandTouchableArea(final View target, final int pixels)
	{
		View parent = ((View) target.getParent());
		parent.post(new Runnable()
		{
			public void run()
			{
				final Rect r = new Rect();
				target.getHitRect(r);
				r.left -= pixels;
				r.top -= pixels;
				r.right += pixels;
				r.bottom += pixels;
				((View) target.getParent()).setTouchDelegate(new TouchDelegate(r, target));
			}
		});
	}
}
