package com.vibeit.overlay;

import android.content.Context;
import android.graphics.Canvas;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.vibeit.R;
import com.vibeit.api.payload.LocationDetailsPayload;

/**
 * @author Maria Dzyokh
 */
public class LocationMapOverlay extends ItemizedOverlay<LocationMapItem> {

    private LocationMapItem locationDetails;

    public LocationMapOverlay(Context ctx, LocationDetailsPayload location) {
        super(null);
        locationDetails = new LocationMapItem(location, ctx.getResources().getDrawable(R.drawable.ic_filter_local_vibes));
        populate();
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        super.draw(canvas, mapView, false);
    }

    @Override
    protected LocationMapItem createItem(int i) {
        return locationDetails;
    }

    @Override
    public int size() {
        return 1;
    }

}
