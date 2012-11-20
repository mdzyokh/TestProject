package com.vibeit.screen;

import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.androidquery.AQuery;
import com.cyberwalkabout.common.expandableadapter.SlideExpandableListAdapter;
import com.cyberwalkabout.common.util.StringUtils;
import com.cyberwalkabout.common.util.Sys;
import com.slidingmenu.lib.SlidingMenu;
import com.vibeit.R;
import com.vibeit.adapter.VibesAdapter;
import com.vibeit.api.model.SurveyReward;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.util.Extras;

/**
 * @author Maria Dzyokh
 */
public class LocationDetailsScreen extends AbstractSlidingFragmentActivity {

    private LocationDetailsPayload locationDetails;

    private RelativeLayout loginPopup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details_screen);
        initLoginPopup();
        loadData();
        initSideMenu();
        initListView();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (loginPopup.getVisibility() == View.VISIBLE) {
                hideLoginPopup();
                return true;
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    private void loadData() {
        locationDetails = (LocationDetailsPayload) getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.data);
        View header = getLayoutInflater().inflate(R.layout.company_details_header, null);
        initHeaderView(header);
        list.addHeaderView(header, null, false);

        VibesAdapter vibesAdapter = new VibesAdapter(this, locationDetails);

        list.setAdapter(new SlideExpandableListAdapter(
                vibesAdapter,
                R.id.expandable_toggle_button,
                R.id.expandable,
                R.id.collapse_toggle_button
        )
        );

    }

    private void initHeaderView(View header) {

        AQuery aQuery = new AQuery(header);

        aQuery.id(R.id.company_logo).image(locationDetails.getLocation().getPictureUrl(), true, false, 0, R.drawable.location_logo_placeholder);
        aQuery.id(R.id.company_name).text(locationDetails.getLocation().getName());
        aQuery.id(R.id.company_address).text(locationDetails.getLocation().getAddress());
        aQuery.id(R.id.rating_bar).rating(locationDetails.getLocation().getAvgOverall());
        aQuery.id(R.id.vibe_count).text(getResources().getQuantityString(R.plurals.vibesCount, locationDetails.getVibes().length, locationDetails.getVibes().length));

        if (locationDetails.hasSurveyReward()) {
            SurveyReward reward = locationDetails.getReward()[0];
            aQuery.id(R.id.vibeit_reward_teaser).visible();
            aQuery.id(R.id.reward_info).text(reward.getName() + " (" + StringUtils.convertToUSDollars(reward.getValue()) + " value)");
        } else {
            aQuery.id(R.id.vibeit_reward_teaser).gone();
        }

        aQuery.id(R.id.btn_vibe_this_business).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getApp().isLoggedIn()) {
                    IntentHelper.ratingScreen(LocationDetailsScreen.this, locationDetails);
                    LocationDetailsScreen.this.finish();
                } else {
                    showLoginPopup();
                }
            }
        });

        aQuery.id(R.id.btn_cancel).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationDetailsScreen.this.finish();
            }
        });

        aQuery.id(R.id.btn_map).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location myLocation = Sys.getLatestKnownLocation(LocationDetailsScreen.this);
                Sys.getDirections(LocationDetailsScreen.this, myLocation.getLatitude(), myLocation.getLongitude(), locationDetails.getLocation().getLatitude(), locationDetails.getLocation().getLongitude());
            }
        });

        aQuery.id(R.id.rewards_indicator).visible();
        aQuery.id(R.id.rewards_indicator).text(String.valueOf(locationDetails.getRewardsCount()));
        aQuery.id(R.id.rewards_indicator).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(SlidingMenu.RIGHT);
            }
        });
    }

    private void initSideMenu() {
        setBehindRightContentView(R.layout.location_rewards);
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow_right, SlidingMenu.RIGHT);
        sm.setBehindOffsetRes(R.dimen.actionbar_home_width, SlidingMenu.RIGHT);
    }

    private void showLoginPopup() {
        loginPopup.setVisibility(View.VISIBLE);
        loginPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
    }

    private void hideLoginPopup() {
        loginPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
        loginPopup.setVisibility(View.GONE);
    }

    private void initLoginPopup() {
        loginPopup = (RelativeLayout) findViewById(R.id.login_popup);
        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.signIn(LocationDetailsScreen.this);
            }
        });
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideLoginPopup();
            }
        });
        loginPopup.setVisibility(View.GONE);
    }
}
