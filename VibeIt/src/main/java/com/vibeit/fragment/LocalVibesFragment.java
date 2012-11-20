package com.vibeit.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.cyberwalkabout.common.util.Sys;
import com.vibeit.R;
import com.vibeit.adapter.CategoriesAdapter;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.util.Settings;
import com.vibeit.widget.SegmentedButtonsRow;
import com.vibeit.widget.VibeFilter;

/**
 * @author Maria Dzyokh
 */
public class LocalVibesFragment extends AbstractFragment {

    public static LocalVibesFragment newInstance() {
        return new LocalVibesFragment();
    }

    @Override
    public void loadData(int page, String nextPageToken, boolean showProgress) {
        android.location.Location lastKnownLocation = Sys.getLatestKnownLocation(getActivity());
        if (lastKnownLocation != null) {
            if (showProgress) {
                showProgress("Loading Locations...");
            }
            float filterDistance = Float.parseFloat(getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_DISTANCE, "0"));
            int filterRating = Integer.parseInt(getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_RATING, "5"));
            String filterOrder = getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_ORDER, "distance");
            int category = getApp().getMainSharedPrefs().getInt(Settings.LOCAL_VIBES_FILTER_CATEGORY, 0);
            VibeItApiUtils.getLocalVibes(getActivity(), locationsReceiver, userToken, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), filterDistance, filterRating, filterOrder, getSearchArgs(), category, page, nextPageToken);
        } else {
                aq.id(android.R.id.empty).text("Unable to determine your location");
        }
    }

    @Override
    public boolean hasMenu() {
        return true;
    }

    @Override
    public View getMenu() {
        prefsChanged = false;
        LinearLayout menu = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.vibes_filter, null);
        if (getActivity() != null) {
            LinearLayout menuHeader = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.local_vibes_filter_header, null);
            VibeFilter distanceFilter = (VibeFilter) menuHeader.findViewById(R.id.distance_filter);
            distanceFilter.setCurrentValue(getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_DISTANCE, "0"));
            distanceFilter.setValueChangedListener(new VibeFilter.ValueChangedListener() {
                @Override
                public void onValueChanged(String newValue) {
                    getApp().getMainSharedPrefs().edit().putString(Settings.LOCAL_VIBES_FILTER_DISTANCE, newValue).commit();
                    prefsChanged = true;
                }
            });

            VibeFilter ratingFilter = (VibeFilter) menuHeader.findViewById(R.id.rating_filter);
            ratingFilter.setCurrentValue(getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_RATING, "5"));
            ratingFilter.setValueChangedListener(new VibeFilter.ValueChangedListener() {
                @Override
                public void onValueChanged(String newValue) {
                    getApp().getMainSharedPrefs().edit().putString(Settings.LOCAL_VIBES_FILTER_RATING, newValue).commit();
                    prefsChanged = true;
                }
            });

            SegmentedButtonsRow orderFilter = (SegmentedButtonsRow) menuHeader.findViewById(R.id.order_filter);
            orderFilter.setCurrentValue(getApp().getMainSharedPrefs().getString(Settings.LOCAL_VIBES_FILTER_ORDER, "by distance"));
            orderFilter.setOnValueChangedListener(new SegmentedButtonsRow.OnValueChangedListener() {
                @Override
                public void onValueChanged(String newValue) {
                    getApp().getMainSharedPrefs().edit().putString(Settings.LOCAL_VIBES_FILTER_ORDER, newValue).commit();
                    prefsChanged = true;
                }
            });
            final ListView categoriesList = (ListView) menu.findViewById(R.id.categories_list);
            categoriesList.addHeaderView(menuHeader);
            categoriesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            categoriesList.setAdapter(new CategoriesAdapter(getActivity(), getApp().getSavedCategories()));
            categoriesList.setItemChecked(getApp().getMainSharedPrefs().getInt(Settings.LOCAL_VIBES_FILTER_CATEGORY, 0) + 1, true);
            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    getApp().getMainSharedPrefs().edit().putInt(Settings.LOCAL_VIBES_FILTER_CATEGORY, (int) l).commit();
                    prefsChanged = true;
                    categoriesList.setItemChecked(i, true);
                }
            });
        }
        return menu;
    }
}
