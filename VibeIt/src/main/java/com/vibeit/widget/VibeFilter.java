package com.vibeit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vibeit.R;

/**
 * @author Maria Dzyokh
 */
public class VibeFilter extends LinearLayout {

    private View root;
    private TextView[] labels;
    private SeekBar seekBar;

    private String[] values;
    private String currentValue;

    private int defaultPosition;
    private int prevPosition;

    public interface ValueChangedListener {
        public void onValueChanged(String newValue);
    }

    private ValueChangedListener listener;

    public VibeFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray customAttributes = context.obtainStyledAttributes(attrs, R.styleable.VibeFilter);

        String[] titles = context.getResources().getStringArray(customAttributes.getResourceId(R.styleable.VibeFilter_labels, 0));

        values = context.getResources().getStringArray(customAttributes.getResourceId(R.styleable.VibeFilter_values, 0));
        defaultPosition = customAttributes.getInt(R.styleable.VibeFilter_defaultPosition, 1);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root = layoutInflater.inflate(R.layout.vibe_filter_layout, this);
        labels = new TextView[6];
        labels[0] = (TextView) root.findViewById(R.id.label1);
        setLabelText(labels[0], titles[0]);
        labels[1] = (TextView) root.findViewById(R.id.label2);
        setLabelText(labels[1], titles[1]);
        labels[2] = (TextView) root.findViewById(R.id.label3);
        setLabelText(labels[2], titles[2]);
        labels[3] = (TextView) root.findViewById(R.id.label4);
        setLabelText(labels[3], titles[3]);
        labels[4] = (TextView) root.findViewById(R.id.label5);
        setLabelText(labels[4], titles[4]);
        labels[5] = (TextView) root.findViewById(R.id.label6);
        setLabelText(labels[5], titles[5]);

        seekBar = (SeekBar) root.findViewById(R.id.seekbar);
        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int newPosition = i;
                updateLabelsColor(newPosition);
                if (prevPosition!=newPosition) {
                    if (listener!=null) {
                        listener.onValueChanged(values[newPosition]);
                    }
                }
                prevPosition = newPosition;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBar.setProgress(defaultPosition);
        prevPosition = defaultPosition;

        customAttributes.recycle();
    }


    private void updateLabelsColor(int currentSelectedIndex) {
        for (int i = 0; i < labels.length; i++) {
            if (i <= currentSelectedIndex) {
                labels[i].setTextColor(Color.parseColor("#3f99d2"));
                continue;
            }
            labels[i].setTextColor(getResources().getColor(android.R.color.white));
        }
    }

    public void setCurrentValue(String arg) {
        this.currentValue = arg;
        int index = defaultPosition;
        for (int i=0; i<values.length; i++) {
            if (values[i].equals(arg)) {
                 index = i+1;
                 break;
            }
        }
        seekBar.setProgress(index);
        prevPosition = index;
    }

    public String getCurrentValue() {
        return this.currentValue;
    }

    public void setValueChangedListener(ValueChangedListener listener) {
        this.listener = listener;
    }

    private void setLabelText(TextView textView, String text) {
        String[] parts = text.split("\n");
        Spannable span = new SpannableString(text);
        span.setSpan(new RelativeSizeSpan(1.5f), 0, parts[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

}
