package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.SurveyPayload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class GetSurveyDetailsAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_SURVEY_ID = "EXTRA_SURVEY_ID";

    private final String userToken;
    private final int surveyId;

    public GetSurveyDetailsAction(Intent intent) {
        super(intent);
        this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        this.surveyId = intent.getIntExtra(EXTRA_SURVEY_ID, 0);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("survey_id", String.valueOf(surveyId));
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/surveys/view";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<SurveyPayload>>(){}.getType();
    }
}
