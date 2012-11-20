package com.vibeit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.astuetz.viewpager.extensions.ViewPagerTabButton;
import com.vibeit.R;
import com.vibeit.model.LocationType;

/**
 * @author Maria Dzyokh
 */
public class HomeScreenTabsAdapter implements TabsAdapter{

    private LayoutInflater li;

    private LocationType[] locations = new LocationType[]{LocationType.LOCAL_VIBES, LocationType.TOP_PERFORMERS, LocationType.RISING_STARS, LocationType.MY_VIBES};

    public HomeScreenTabsAdapter(Context ctx) {
        this.li = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position) {
        ViewPagerTabButton tab;
        tab = (ViewPagerTabButton) li.inflate(R.layout.tab_fixed, null);
        if (position < locations.length) tab.setText(locations[position].getName());
        return tab;
    }
}
