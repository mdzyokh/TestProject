package com.vibeit.drawable;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

/**
 * @author Andrii Kovalov
 */
public class VerticalDrawable extends BitmapDrawable
{
	public VerticalDrawable(Resources res, int image)
	{
		super(BitmapFactory.decodeResource(res, image));
		setTileModeY(Shader.TileMode.REPEAT);
	}
}
