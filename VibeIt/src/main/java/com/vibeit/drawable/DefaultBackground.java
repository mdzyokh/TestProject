package com.vibeit.drawable;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import com.vibeit.R;

import java.lang.ref.WeakReference;

/**
 * @author Andrii Kovalov
 */
public class DefaultBackground extends LayerDrawable {
	private WeakReference<Context> ctxRef;

	public static DefaultBackground create(Context ctx) {
		ShapeDrawable rect = new ShapeDrawable(new RectShape());
		rect.getPaint().setColor(0xff4992d1);
		BitmapDrawable pattern = new BitmapDrawable(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.texture_background));
		pattern.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		DefaultBackground background = new DefaultBackground(new Drawable[]{rect, pattern});
		background.ctxRef = new WeakReference<Context>(ctx);
		return background;
	}

	private DefaultBackground(Drawable[] layers) {
		super(layers);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		int yOffset = -30;
		RadialGradient gradient = new RadialGradient(canvas.getWidth() / 2, yOffset, canvas.getWidth() / 2, 0x90ffffff, 0x904992d1, android.graphics.Shader.TileMode.CLAMP);
		Paint p = new Paint();
		p.setDither(true);
		p.setShader(gradient);
		canvas.drawRect(canvas.getClipBounds(), p);
	}
}