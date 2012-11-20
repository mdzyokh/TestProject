package com.vibeit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.androidquery.AQuery;
import com.vibeit.R;
import com.vibeit.animation.ExpandAnimation;
import com.vibeit.animation.HeightResizeAnimation;
import com.vibeit.api.model.SurveyChoice;
import com.vibeit.api.model.SurveyQuestion;
import com.vibeit.api.payload.SurveyPayload;
import com.vibeit.widget.CustomFontRadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maria dzyokh
 */
public class SurveyQuestionsAdapter extends BaseAdapter {


    private SurveyPayload surveyPayload;
    private ArrayList<SurveyChoice> userChoices;

    private LayoutInflater li;

    private SurveyCompletedListener listener;

    private int counter;

    public SurveyQuestionsAdapter(Context ctx, SurveyPayload surveyPayload, SurveyCompletedListener listener) {
        this.surveyPayload = surveyPayload;
        this.userChoices = new ArrayList<SurveyChoice>(surveyPayload.getQuestions().length);
        this.li = LayoutInflater.from(ctx);
        this.listener = listener;
        this.counter = 0;
    }

    @Override
    public int getCount() {
        return surveyPayload.getQuestions().length;
    }

    @Override
    public SurveyQuestion getItem(int position) {
        return surveyPayload.getQuestions()[position];
    }

    @Override
    public long getItemId(int position) {
        return surveyPayload.getQuestions()[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = li.inflate(R.layout.survey_question, null);
        }

        AQuery aq = new AQuery(convertView);

        aq.id(R.id.question_number).text((position + 1) + ". ");
        aq.id(R.id.question_text).text(getItem(position).getQuestion());

        List<SurveyChoice> questionChoices = surveyPayload.getQuestionChoices(getItem(position).getId());

        RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.answers);
        for (SurveyChoice choice : questionChoices) {
            CustomFontRadioButton button = (CustomFontRadioButton) li.inflate(R.layout.survey_question_choice, null);
            button.setText(choice.getChoice());
            button.setTag(choice);
            rg.addView(button, new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                CustomFontRadioButton button = (CustomFontRadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                SurveyChoice choice = (SurveyChoice) button.getTag();
                userChoices.add(choice);

                HeightResizeAnimation animation = new HeightResizeAnimation((RelativeLayout)radioGroup.getParent(), 0, false);
                animation.setDuration(700);
                animation.setFillAfter(true);

                ((RelativeLayout)radioGroup.getParent()).startAnimation(animation);

                counter++;

                if (counter == surveyPayload.getQuestions().length) {
                    if (listener!=null) {
                        listener.onSurveyCompleted(userChoices, surveyPayload.getSurvey().getId());
                    }
                }
            }
        });

        return convertView;
    }

    public interface SurveyCompletedListener {
        public void onSurveyCompleted(ArrayList<SurveyChoice> choices, int surveyId);
    }
}
