package com.vibeit.overlay;

import android.graphics.drawable.Drawable;
import com.cyberwalkabout.common.util.ConvertUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.vibeit.api.payload.LocationDetailsPayload;

/**
 * @author Maria Dzyokh
 */
public class LocationMapItem extends OverlayItem {

    private Drawable marker = null;
    private LocationDetailsPayload locationDetails;

    LocationMapItem(LocationDetailsPayload locationDetails, Drawable marker) {
        super(ConvertUtils.toGeoPoint(locationDetails.getLocation().getLatitude(), locationDetails.getLocation().getLongitude()), locationDetails.getLocation().getName(), "");
        this.marker = marker;
        this.locationDetails = locationDetails;
    }

    @Override
    public Drawable getMarker(int stateBitset) {
        setState(marker, stateBitset);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        return marker;
    }

    public LocationDetailsPayload getLocationDetails() {
        return this.locationDetails;
    }
}
