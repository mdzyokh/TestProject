package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.LocationPayload;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class GetLocationDetailsAction extends PostAbstractAction {

    public static final String EXTRA_SERVICE_HASH = "EXTRA_SERVICE_HASH";
    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";

    private final String serviceHash;
    private final String userToken;

    public GetLocationDetailsAction(Intent intent) {
        super(intent);
        this.serviceHash = intent.getStringExtra(EXTRA_SERVICE_HASH);
        this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("service_hash", serviceHash);
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/locations/view";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<LocationDetailsPayload>>() {}.getType();
    }
}
