package com.vibeit.widget;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;

import java.util.Collection;

/**
 * @author Maria Dzyokh
 */
public class CustomFontCheckableTextView extends CustomFontTextView implements Checkable{

    private boolean checked = false;

    public CustomFontCheckableTextView(Context context) {
        super(context);
    }

    public CustomFontCheckableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontCheckableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setChecked(boolean b) {
        if (checked!=b) {
            checked = b;
        }
        if(checked) {
           setBackgroundColor(Color.parseColor("#880099CC"));
        } else {
            setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
