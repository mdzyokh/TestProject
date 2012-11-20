package com.vibeit.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.cyberwalkabout.common.util.Sys;
import com.vibeit.R;
import com.vibeit.adapter.CategoriesAdapter;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.util.Settings;
import com.vibeit.widget.VibeFilter;

/**
 * @author Maria Dzyokh
 */
public class TopPerformersFragment extends AbstractFragment {

    public static TopPerformersFragment newInstace() {
        return new TopPerformersFragment();
    }

    @Override
    public void loadData(int page, String nextPageToken, boolean showProgress) {
        android.location.Location lastKnownLocation = Sys.getLatestKnownLocation(getActivity());
        if (lastKnownLocation != null) {
            if (showProgress) {
                showProgress(getString(R.string.loading_locations));
            }
            float filterDistance = Float.parseFloat(getApp().getMainSharedPrefs().getString(Settings.TOP_PERFORMERS_FILTER_DISTANCE, "0"));
            int category = getApp().getMainSharedPrefs().getInt(Settings.TOP_PERFORMERS_FILTER_CATEGORY, 0);
            VibeItApiUtils.getTopPerformers(getActivity(), locationsReceiver, userToken, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), filterDistance, category, getSearchArgs(), page, nextPageToken);
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
            LinearLayout menuHeader = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.best_of_the_best_filter_header, null);
            ((TextView) menuHeader.findViewById(R.id.filter_title)).setText("Top Performers Filter");
            VibeFilter distanceFilter = (VibeFilter) menuHeader.findViewById(R.id.distance_filter);
            distanceFilter.setCurrentValue(getApp().getMainSharedPrefs().getString(Settings.TOP_PERFORMERS_FILTER_DISTANCE, "0"));
            distanceFilter.setValueChangedListener(new VibeFilter.ValueChangedListener() {
                @Override
                public void onValueChanged(String newValue) {
                    getApp().getMainSharedPrefs().edit().putString(Settings.TOP_PERFORMERS_FILTER_DISTANCE, newValue).commit();
                    prefsChanged = true;
                }
            });
            final ListView categoriesList = (ListView) menu.findViewById(R.id.categories_list);
            categoriesList.addHeaderView(menuHeader);
            categoriesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            categoriesList.setAdapter(new CategoriesAdapter(getActivity(), getApp().getSavedCategories()));
            categoriesList.setItemChecked(getApp().getMainSharedPrefs().getInt(Settings.TOP_PERFORMERS_FILTER_CATEGORY, 0) + 1, true);
            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    getApp().getMainSharedPrefs().edit().putInt(Settings.TOP_PERFORMERS_FILTER_CATEGORY, (int) l).commit();
                    prefsChanged = true;
                    categoriesList.setItemChecked(i, true);
                }
            });
        }
        return menu;
    }
}
