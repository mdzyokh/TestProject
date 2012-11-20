package com.vibeit.screen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.readystatesoftware.viewbadger.BadgeView;
import com.sugree.twitter.TwitterConnector;
import com.vibeit.R;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.SurveyPayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.util.Extras;
import com.vibeit.util.Settings;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.sugree.twitter.TwitterConnector;

/**
 * @author Maria Dzyoh
 */
public class RatingStep2Screen extends AbstractActivity implements AbstractActivity.OnFocusLostListener {

    private ApiResultReceiver locationReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<LocationDetailsPayload> response = (ApiResponse<LocationDetailsPayload>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    IntentHelper.locationDetailsScreen(RatingStep2Screen.this, response.getData());
                    RatingStep2Screen.this.finish();
                } else {
                    Toast.makeText(RatingStep2Screen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(RatingStep2Screen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private ApiResultReceiver surveyReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<SurveyPayload> response = (ApiResponse<SurveyPayload>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    IntentHelper.surveyScreen(RatingStep2Screen.this, location, response.getData());
                } else {
                    Toast.makeText(RatingStep2Screen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(RatingStep2Screen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private LocationDetailsPayload location;

    private boolean isKeyboardShowing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.rating_screen_step_2);
        loadData();
        initViews();
        initCharacterCounter();

        setOnFocusLostListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentHelper.locationDetailsScreen(RatingStep2Screen.this, location);
    }

    private void loadData() {
        location = (LocationDetailsPayload) getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
    }

    private void initViews() {
        aq.id(R.id.average_rating).rating(location.getLocation().getAverageRating());
        aq.id(R.id.arrow_up).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.locationDetailsScreen(RatingStep2Screen.this, location);
                RatingStep2Screen.this.finish();
            }
        });

        aq.id(R.id.company_logo).image(location.getLocation().getPictureUrl());
        aq.id(R.id.company_name).text(location.getLocation().getName());
        aq.id(R.id.company_address).text(location.getLocation().getAddress());

        if (location.hasSurveyReward()) {
            aq.id(R.id.reward_banner).visible();
        } else {
            aq.id(R.id.reward_banner).gone();
        }

        setupRateEmployeeButton();
        setupSurveyButton();

        aq.id(R.id.btn_submit_vibe).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                VibeItApiUtils.submitVibe(RatingStep2Screen.this, locationReceiver, getApp().getUserToken(), location.getLocation().getId(), location.getLocation().getGoalSpeed(), location.getLocation().getGoalProffesionalism(), location.getLocation().getGoalCleanliness(), location.getLocation().getGoalValue(), aq.id(R.id.comment).getText().toString().trim());

            }
        });

        if (getApp().getMainSharedPrefs().getBoolean(Settings.SETTING_TWITTER_SHARING, false)) {
            aq.id(R.id.social_sharing_twitter).enabled(true);
            aq.id(R.id.social_sharing_twitter).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareTwitter();
                }
            });
        } else {
            aq.id(R.id.social_sharing_twitter).enabled(false);
        }

        if (getApp().getMainSharedPrefs().getBoolean(Settings.SETTING_FACEBOOK_SHARING, false)) {
            aq.id(R.id.social_sharing_facebook).enabled(true);
            aq.id(R.id.social_sharing_facebook).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareFacebook();
                }
            });
        } else {
            aq.id(R.id.social_sharing_facebook).enabled(false);
        }


    }

    private void initCharacterCounter() {
        EditText editText = (EditText) findViewById(R.id.comment);
        final BadgeView badgeView = new BadgeView(this, editText);
        badgeView.setText("140");
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setBadgeMargin(10, 10);
        badgeView.setBackgroundResource(R.drawable.badge_bkg);
        badgeView.setTextColor(getResources().getColor(android.R.color.white));
        badgeView.setTextSize(14);
        badgeView.setGravity(Gravity.CENTER);
        badgeView.show();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                badgeView.setText(String.valueOf(140 - charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isKeyboardShowing) {
                    isKeyboardShowing = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        });
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isKeyboardShowing) {
                isKeyboardShowing = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setupSurveyButton() {
        if (location.hasSurvey()) {
            aq.id(R.id.btn_take_survey).enabled(true);
            aq.id(R.id.btn_take_survey_disabled).gone();
            aq.id(R.id.btn_take_survey).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProgress();
                    VibeItApiUtils.getSurveyDetails(RatingStep2Screen.this, surveyReceiver, getApp().getUserToken(), location.getSurvey()[0].getId());
                }
            });
        } else {
            aq.id(R.id.btn_take_survey).enabled(false);
            aq.id(R.id.btn_take_survey_disabled).visible();
        }
    }

    private void setupRateEmployeeButton() {
        if (location.hasEmployees()) {
            aq.id(R.id.btn_rate_employee).enabled(true);
            aq.id(R.id.btn_rate_employee_disabled).gone();
            aq.id(R.id.btn_rate_employee).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentHelper.employeeRatingScreen(RatingStep2Screen.this, location);
                }
            });
        } else {
            aq.id(R.id.btn_rate_employee).enabled(false);
            aq.id(R.id.btn_rate_employee_disabled).visible();
        }
    }


    private void shareTwitter() {
        TwitterConnector twitterConnector = TwitterConnector.getInstance(RatingStep2Screen.this, R.drawable.twitter_sharing_toggle, getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret));
        twitterConnector.tweetWithAuthorizing(RatingStep2Screen.this, aq.id(R.id.comment).getText().toString().trim());
    }


    private void shareFacebook() {
        FacebookHandle facebookHandle = new FacebookHandle(RatingStep2Screen.this, getString(R.string.facebook_app_id), "publish_stream, email");
        String url = "https://graph.facebook.com/me/feed";
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("message", aq.id(R.id.comment).getText().toString().trim()));
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AQuery.POST_ENTITY, entity);
            aq.auth(facebookHandle).ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    if (status.getMessage().equals("OK")) {
                        Toast.makeText(RatingStep2Screen.this, "Message posted", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject error = new JSONObject(status.getError());
                            String msg = error.optString("message");
                            Toast.makeText(RatingStep2Screen.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } catch (Exception e) {
            Log.e(RatingStep2Screen.class.getSimpleName(), e.getMessage(), e);
        }

    }


    @Override
    public void onFocusLost(View view) {
        if (view.getId() == R.id.comment) {
            if (isKeyboardShowing) {
                isKeyboardShowing = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
}
