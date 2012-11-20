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
public class GetLocalVibesAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_DISTANCE = "EXTRA_DISTANCE";
    public static final String EXTRA_AVG_OVERALL = "EXTRA_AVG_OVERALL";
    public static final String EXTRA_ORDER = "EXTRA_ORDER";
    public static final String EXTRA_SEARCH = "EXTRA_SEARCH";
    public static final String EXTRA_PAGE = "EXTRA_PAGE";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String EXTRA_NEXT_PAGE_TOKEN = "EXTRA_NEXT_PAGE_TOKEN";

    private final String userToken;
    private final double latitude;
    private final double longitude;
    private final float distance;
    private final int avgOverall;
    private final String order;
    private final SearchArgs searchArgs;
    private final int page;
    private final int category;
    private final String nextPageToken;

    public GetLocalVibesAction(Intent intent) {
        super(intent);
        this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        this.latitude = intent.getDoubleExtra(EXTRA_LATITUDE, 0);
        this.longitude = intent.getDoubleExtra(EXTRA_LONGITUDE, 0);
        this.distance = intent.getFloatExtra(EXTRA_DISTANCE, 0.0f);
        this.avgOverall = intent.getIntExtra(EXTRA_AVG_OVERALL, 0);
        this.order = intent.getStringExtra(EXTRA_ORDER);
        this.searchArgs = intent.getParcelableExtra(EXTRA_SEARCH);
        this.page = intent.getIntExtra(EXTRA_PAGE, 1);
        this.category = intent.getIntExtra(EXTRA_CATEGORY, 0);
        this.nextPageToken = intent.getStringExtra(EXTRA_NEXT_PAGE_TOKEN);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("user_token", userToken);
        requestBody.add("lat", String.valueOf(latitude));
        requestBody.add("lng", String.valueOf(longitude));
        requestBody.add("distance", distance == 0.0f ? "" : String.valueOf(distance));
        requestBody.add("avg_overall", String.valueOf(avgOverall));
        requestBody.add("order", order);
        requestBody.add("search", searchArgs.getQuery());
        requestBody.add("address", searchArgs.getAddress());
        requestBody.add("page", String.valueOf(page));
        requestBody.add("category", String.valueOf(category));
        requestBody.add("next_page_token", nextPageToken);
        return requestBody;
    }

    @Override
    protected String getUrl() {
        return "/api/locations/localVibes";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<LocationPayload>>() {
        }.getType();
    }
}
