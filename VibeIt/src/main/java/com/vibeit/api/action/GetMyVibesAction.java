package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.LocationPayload;
import com.vibeit.model.SearchArgs;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class GetMyVibesAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_PAGE = "EXTRA_PAGE";
    public static final String EXTRA_SEARCH = "EXTRA_SEARCH";
    public static final String EXTRA_NEXT_PAGE_TOKEN = "EXTRA_NEXT_PAGE_TOKEN";

    private final String userToken;
    private final SearchArgs searchArgs;
    private final int page;
    private final String nextPageToken;

    public GetMyVibesAction(Intent intent) {
        super(intent);
        this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        this.page = intent.getIntExtra(EXTRA_PAGE, 1);
        this.searchArgs = intent.getParcelableExtra(EXTRA_SEARCH);
        this.nextPageToken = intent.getStringExtra(EXTRA_NEXT_PAGE_TOKEN);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("search", searchArgs.getQuery());
        requestBody.add("address", searchArgs.getAddress());
        requestBody.add("page", String.valueOf(page));
        requestBody.add("next_page_token", nextPageToken);
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/locations/myVibes";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<LocationPayload>>() {
        }.getType();
    }
}