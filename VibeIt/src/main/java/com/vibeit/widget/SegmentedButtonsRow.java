package com.vibeit.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.vibeit.R;

/**
 * @author Maria Dzyokh
 */
public class SegmentedButtonsRow extends LinearLayout {

    private OnValueChangedListener listener;
    private int activeButtonIndex;
    private Button[] createdButtons;

    private String[] titles;
    private String[] values;

    private Drawable activeButtonDrawable;
    private Drawable passiveButtonDrawable;


    public SegmentedButtonsRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((Activity) getContext()).getLayoutInflater().inflate(R.layout.segmented_buttons_row, this);

        TypedArray customAttributes = context.obtainStyledAttributes(attrs, R.styleable.SegmentedButtonsRow);

        values = context.getResources().getStringArray(customAttributes.getResourceId(R.styleable.SegmentedButtonsRow_vals, 0));
        titles = context.getResources().getStringArray(customAttributes.getResourceId(R.styleable.SegmentedButtonsRow_titles, 0));
        activeButtonIndex = customAttributes.getInt(R.styleable.SegmentedButtonsRow_selectedPosition, 1) - 1;

        createdButtons = new Button[titles.length];
        LinearLayout root = (LinearLayout) findViewById(R.id.rootPanel);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue_Bold.ttf");

        for (int i = 0; i < titles.length; i++) {
            createdButtons[i] = new Button(context);
            createdButtons[i].setTypeface(tf);
            createdButtons[i].setText(titles[i]);
            createdButtons[i].setTag(i);
            createdButtons[i].setBackgroundResource(R.drawable.order_vibes_by_bg);
            createdButtons[i].setTextSize(12.0f);
            createdButtons[i].setPadding(-4, 10, -4, 10);
            createdButtons[i].setCompoundDrawablePadding(5);
            createdButtons[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) listener.onValueChanged(values[(Integer) view.getTag()]);
                    activeButtonIndex = (Integer) view.getTag();
                    setBackgrounds();
                }
            });
            root.addView(createdButtons[i], new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));

            if (i != titles.length - 1) {
                View divider = new View(context);
                divider.setBackgroundResource(R.drawable.divider_vertical_order_vibes_by);
                root.addView(divider, new LayoutParams(2, LayoutParams.FILL_PARENT));
            }
        }

        activeButtonDrawable = getContext().getResources().getDrawable(R.drawable.circle_active);
        activeButtonDrawable.setBounds(0, 0, activeButtonDrawable.getIntrinsicWidth(), activeButtonDrawable.getIntrinsicHeight());
        passiveButtonDrawable = getContext().getResources().getDrawable(R.drawable.circle_passive);
        passiveButtonDrawable.setBounds(0, 0, passiveButtonDrawable.getIntrinsicWidth(), passiveButtonDrawable.getIntrinsicHeight());

        customAttributes.recycle();
        setBackgrounds();
    }

    private void setBackgrounds() {
        for (int i = 0; i < createdButtons.length; i++) {
            Drawable d;
            String textColor;
            if (activeButtonIndex == i) {
                textColor = "#3f99d2";
                d = activeButtonDrawable;
            } else {
                textColor = "#ffffff";
                d = passiveButtonDrawable;
            }
            createdButtons[i].setTextColor(Color.parseColor(textColor));
            createdButtons[i].setCompoundDrawables(null, d, null, null);
        }
    }

    public void setCurrentValue(String arg) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(arg)) {
                activeButtonIndex = i;
                setBackgrounds();
                break;
            }
        }

    }

    public void setOnValueChangedListener(OnValueChangedListener listener) {
        this.listener = listener;
    }

    public interface OnValueChangedListener {
        public void onValueChanged(String newValue);
    }

}
