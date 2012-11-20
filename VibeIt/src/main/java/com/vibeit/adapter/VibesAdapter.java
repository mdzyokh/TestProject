package com.vibeit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.androidquery.AQuery;
import com.cyberwalkabout.common.util.UiUtils;
import com.vibeit.R;
import com.vibeit.api.model.LocationVibe;
import com.vibeit.api.model.VibeAuthor;
import com.vibeit.api.payload.LocationDetailsPayload;

/**
 * @author Maria Dzyokh
 */
public class VibesAdapter extends BaseAdapter {

    private LocationDetailsPayload locationDetails;
    private LayoutInflater li;

    public VibesAdapter(Context ctx, LocationDetailsPayload locationDetails) {
        li = LayoutInflater.from(ctx);
        this.locationDetails = locationDetails;
    }

    @Override
    public int getCount() {
        return locationDetails.getVibes().length;
    }

    @Override
    public LocationVibe getItem(int position) {
        return locationDetails.getVibes()[position];
    }

    @Override
    public long getItemId(int position) {
        return locationDetails.getVibes()[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = li.inflate(R.layout.comment, null);
        }

        AQuery aq = new AQuery(convertView);

        VibeAuthor vibeAuthor = getVibeAuthor(getItem(position).getUserId());
        aq.id(R.id.author_name).text(vibeAuthor.getFirstName() + " " + vibeAuthor.getLastName().substring(0, 1) + ".");
        aq.id(R.id.overall_rating).rating(getItem(position).getOverall());
        aq.id(R.id.speed_of_service).rating(getItem(position).getSpeed());
        aq.id(R.id.professionalisn_of_staff).rating(getItem(position).getProfessionalism());
        aq.id(R.id.cleanlines_of_business).rating(getItem(position).getCleanliness());
        aq.id(R.id.value_of_product).rating(getItem(position).getValue());

        if (getItem(position).hasComment()) {
            aq.id(R.id.comment_text).text(getItem(position).getComment());
            aq.id(R.id.comment_text).visible();
        } else {
            aq.id(R.id.comment_text).gone();
        }


        UiUtils.expandTouchableArea(aq.id(R.id.expandable_toggle_button).getView(), 20);
        UiUtils.expandTouchableArea(aq.id(R.id.collapse_toggle_button).getView(), 20);

        return convertView;
    }


    private VibeAuthor getVibeAuthor(int id) {
        VibeAuthor author = null;
        for (int i = 0; i < locationDetails.getAuthors().length; i++) {
            VibeAuthor temp = locationDetails.getAuthors()[i];
            if (temp.getId() == id) {
                author = temp;
                break;
            }
        }
        return author;
    }

}
