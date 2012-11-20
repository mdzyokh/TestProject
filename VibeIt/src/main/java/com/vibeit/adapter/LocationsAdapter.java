package com.vibeit.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.androidquery.AQuery;
import com.cyberwalkabout.common.util.DistanceUtils;
import com.cyberwalkabout.common.util.StringUtils;
import com.vibeit.R;
import com.vibeit.api.model.Location;
import com.vibeit.api.model.SurveyReward;
import com.vibeit.api.payload.LocationPagePayload;

/**
 * @author Maria Dzyokh
 */
public class LocationsAdapter extends ArrayAdapter<LocationPagePayload> {

    private LayoutInflater li;
    private AQuery aq;
    private Context ctx;

    public LocationsAdapter(Context ctx, LocationPagePayload[] locations) {
        super(ctx, R.layout.home_screen_venue_cell);
        li = LayoutInflater.from(ctx);
        this.ctx = ctx;

        if (Build.VERSION.SDK_INT >= 11) {
            addAll(locations);
        } else {
            for (LocationPagePayload item : locations) {
                add(item);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getLocation().getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = li.inflate(R.layout.home_screen_venue_cell, null);
        }
        aq = new AQuery(view);
        Location location = getItem(position).getLocation();
        aq.id(R.id.location_name).text(location.getName());
        aq.id(R.id.location_address).text(location.getAddress());
        aq.id(R.id.location_rating).rating(location.getAvgOverall());
        aq.id(R.id.distance_to_location).text(DistanceUtils.getDistanceToLocationStr(ctx, "#.#", location.getLatitude(), location.getLongitude()) + " mi");

        if (getItem(position).hasSurveyReward()) {
            SurveyReward reward = getItem(position).getReward()[0];
            aq.id(R.id.reward_section).visible();
            aq.id(R.id.reward_info).text(reward.getName()+" ("+ StringUtils.convertToUSDollars(reward.getValue())+" value)");
        } else {
            aq.id(R.id.reward_section).gone();
        }

        return view;
    }
}
