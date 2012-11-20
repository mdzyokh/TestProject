package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.LocationDetailsPayload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class SubmitVibeAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_LOCATION_ID = "EXTRA_LOCATION_ID";
    public static final String EXTRA_SPEED = "EXTRA_SPEED";
    public static final String EXTRA_PROFESSIONALISM = "EXTRA_PROFESSIONALISM";
    public static final String EXTRA_CLEANLINESS = "EXTRA_CLEANLINESS";
    public static final String EXTRA_VALUE = "EXTRA_VALUE";
    public static final String EXTRA_COMMENT = "EXTRA_COMMENT";

    private final String userToken;
    private final int locationId;
    private final float speed;
    private final float professionalism;
    private final float cleanliness;
    private final float value;
    private final String comment;

    public SubmitVibeAction(Intent intent) {
        super(intent);
        userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        locationId = intent.getIntExtra(EXTRA_LOCATION_ID, 0);
        speed = intent.getFloatExtra(EXTRA_SPEED, 0.0f);
        professionalism = intent.getFloatExtra(EXTRA_PROFESSIONALISM, 0.0f);
        cleanliness = intent.getFloatExtra(EXTRA_CLEANLINESS, 0.0f);
        comment = intent.getStringExtra(EXTRA_COMMENT);
        value = intent.getFloatExtra(EXTRA_VALUE, 0.0f);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("location_id", String.valueOf(locationId));
        requestBody.add("speed", String.valueOf(speed));
        requestBody.add("professionalism", String.valueOf(professionalism));
        requestBody.add("cleanliness", String.valueOf(cleanliness));
        requestBody.add("value", String.valueOf(value));
        requestBody.add("comment", comment);
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/locations/vibeIt";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<LocationDetailsPayload>>() {
        }.getType();
    }
}
