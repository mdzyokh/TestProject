package com.vibeit.adapter;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vibeit.api.model.UserQuestionChoice;

/**
 * @author Maria Dzyokh
 */
public class UserQuestionChoiceSpinnerAdapter extends BaseAdapter {

    private LayoutInflater li;
    private int layoutResourceId;
    private UserQuestionChoice[] choices;

    public UserQuestionChoiceSpinnerAdapter(Context context, UserQuestionChoice[] choices) {
        this.li = LayoutInflater.from(context);
        this.layoutResourceId = R.layout.simple_spinner_item;
        this.choices = choices;
    }

    @Override
    public int getCount() {
        return choices.length;
    }

    @Override
    public UserQuestionChoice getItem(int position) {
        return choices[position];
    }

    @Override
    public long getItemId(int position) {
        return choices[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView == null) {
           convertView = li.inflate(layoutResourceId, null);
       }
        ((TextView)convertView).setText(choices[position].getChoice());
        convertView.setPadding(15, 15, 15, 15);
        return convertView;
    }
}
