package com.vibeit.api.action;

import android.content.Intent;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.model.SurveyChoice;
import com.vibeit.api.model.SurveyReward;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Maria Dzyokh
 */
public class TakeSurveyAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_LOCATION_ID = "EXTRA_LOCATION_ID";
    public static final String EXTRA_SURVEY_ID= "EXTRA_SURVEY_ID";
    public static final String EXTRA_ANSWERS = "EXTRA_ANSWERS";

    private final String userToken;
    private final int locationId;
    private final int surveyId;
    private final List<SurveyChoice> answers;

    public TakeSurveyAction(Intent intent) {
        super(intent);
        userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        locationId = intent.getIntExtra(EXTRA_LOCATION_ID, 0);
        surveyId = intent.getIntExtra(EXTRA_SURVEY_ID, 0);
        answers = (List<SurveyChoice>) intent.getSerializableExtra(EXTRA_ANSWERS);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("location_id", String.valueOf(locationId));
        requestBody.add("survey_id", String.valueOf(surveyId));
        requestBody.add("answers", composeQuestionAnswersJson());
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/surveys/take";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<SurveyReward>>() {}.getType();
    }

    public String composeQuestionAnswersJson() {
        String result = "";
        try {
            JSONArray array = new JSONArray();
            for (SurveyChoice choice : answers) {
                JSONObject answer = new JSONObject();
                answer.put("survey_question_id", choice.getSurveyQuestionId());
                answer.put("survey_question_choice_id", choice.getId());
                array.put(answer);
            }
            result = array.toString();
        } catch (JSONException e) {
            Log.e(TakeSurveyAction.class.getSimpleName(), e.getMessage(), e);
        }
        return result;
    }
}
