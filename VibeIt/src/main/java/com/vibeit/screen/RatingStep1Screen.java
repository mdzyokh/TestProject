package com.vibeit.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import com.cyberwalkabout.common.util.StringUtils;
import com.vibeit.R;
import com.vibeit.api.model.SurveyReward;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.util.Extras;

/**
 * @author Maria Dzyokh
 */
public class RatingStep1Screen extends AbstractActivity {

    private LocationDetailsPayload location;

    private boolean hasSpeed = false;
    private boolean hasProfesionalism = false;
    private boolean hasCleanliness = false;
    private boolean hasValue = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_screen_step_1);
        loadData();
        initViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentHelper.locationDetailsScreen(RatingStep1Screen.this, location);
    }

    private void loadData() {
        location = (LocationDetailsPayload) getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
    }

    private void initViews() {
        aq.id(R.id.company_name).text(location.getLocation().getName());
        if (location.hasSurveyReward()) {
            SurveyReward reward = location.getReward()[0];
            aq.id(R.id.vibeit_reward_teaser).visible();
            aq.id(R.id.reward_info).text(reward.getName()+" ("+ StringUtils.convertToUSDollars(reward.getValue())+" value)");
        } else {
            aq.id(R.id.vibeit_reward_teaser).gone();
        }
        aq.id(R.id.btn_cancel).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.locationDetailsScreen(RatingStep1Screen.this, location);
                RatingStep1Screen.this.finish();
            }
        });

        ((RatingBar)findViewById(R.id.speed_of_service_rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                 location.getLocation().setGoalSpeed(v);
                 hasSpeed = true;
                checkInput();
            }
        });

        ((RatingBar)findViewById(R.id.professionalism_rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                location.getLocation().setGoalProffesionalism(v);
                hasProfesionalism = true;
                checkInput();
            }
        });

        ((RatingBar)findViewById(R.id.cleanliness_rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                location.getLocation().setGoalCleanliness(v);
                hasCleanliness = true;
                checkInput();
            }
        });

        ((RatingBar)findViewById(R.id.product_value_rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                location.getLocation().setGoalValue(v);
                hasValue = true;
                checkInput();
            }
        });

    }

    private void checkInput() {
        if (hasCleanliness & hasProfesionalism & hasSpeed & hasValue) {
            aq.id(R.id.btn_continue).enabled(true);
            aq.id(R.id.btn_continue).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentHelper.ratingStep2Screen(RatingStep1Screen.this, location);
                    RatingStep1Screen.this.finish();
                }
            });
        }
    }
}
