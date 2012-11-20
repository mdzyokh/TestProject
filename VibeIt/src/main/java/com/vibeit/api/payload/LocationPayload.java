package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;

/**
 * @author Maria Dzyokh
 */
public class LocationPayload extends BasicPayload {

    @SerializedName("locations")
    private LocationPagePayload[] locations;
    @SerializedName("page")
    private int page;
    @SerializedName("is_last")
    private boolean isLast;
    @SerializedName("next_page_token")
    private String nextPageToken;

    public LocationPagePayload[] getLocations() {
        return locations;
    }

    public void setLocations(LocationPagePayload[] locations) {
        this.locations = locations;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
