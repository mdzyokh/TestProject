package com.vibeit.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.astuetz.viewpager.extensions.FixedTabsView;
import com.slidingmenu.lib.SlidingMenu;
import com.sugree.twitter.DialogError;
import com.sugree.twitter.Twitter;
import com.sugree.twitter.TwitterConnector;
import com.sugree.twitter.TwitterError;
import com.vibeit.R;
import com.vibeit.adapter.HomeScreenPagerAdapter;
import com.vibeit.adapter.HomeScreenTabsAdapter;
import com.vibeit.animation.ExpandAnimation;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.payload.CategoryPayload;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.fragment.*;
import com.vibeit.intent.IntentHelper;
import com.vibeit.model.SearchArgs;
import com.vibeit.util.LocalPersistStorage;
import com.vibeit.util.Settings;
import com.vibeit.util.SocialShareUtils;
import com.vibeit.util.UiUtils;
import com.vibeit.widget.SwitchICS;
import org.json.JSONObject;

/**
 * @author Maria Dzyokh
 */
public class HomeScreen extends AbstractSlidingFragmentActivity {

    private static final int ACTIVITY_SSO = 1;

    private SlidingMenu sm;
    private ViewPager pager;
    private FixedTabsView fixedTabs;

    private View editProfileView;

    private AbstractFragment[] fragments;

    private FacebookHandle facebookHandle;
    private TwitterConnector twitterConnector;

    private Bundle savedInstanceState;

    private ApiResultReceiver editProfileReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<UserResponsePayload> response = (ApiResponse<UserResponsePayload>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (response.isSuccess()) {
                    getApp().saveUserInfo(response.getPayload());
                } else {
                    Toast.makeText(HomeScreen.this, response.getError(), Toast.LENGTH_LONG).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(HomeScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private ApiResultReceiver categoriesReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<CategoryPayload[]> response = (ApiResponse<CategoryPayload[]>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    LocalPersistStorage.witeObjectToFile(HomeScreen.this, response.getData(), Settings.CATEGORIES);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setUpSideMenu();
                            getFragment(0).loadData();
                        }
                    }, 100);
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(HomeScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        this.savedInstanceState = savedInstanceState;
        facebookHandle = new FacebookHandle(this, getString(R.string.facebook_app_id), "publish_stream, email");
        facebookHandle.sso(ACTIVITY_SSO);

        initFragments();
        initSideMenu();
        initViewPager();
        fixedTabs = (FixedTabsView) findViewById(R.id.tabs);
        fixedTabs.setAdapter(new HomeScreenTabsAdapter(this));
        fixedTabs.setViewPager(pager);

        initSearchField();

        requestCategories();
    }

    @Override
    public void onResume() {
        super.onResume();
        initActionButtons();
        initUserSettings();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (editProfileView != null && editProfileView.getVisibility() == View.VISIBLE) {
                editProfileView.startAnimation(AnimationUtils.loadAnimation(HomeScreen.this, R.anim.slide_down));
                editProfileView.setVisibility(View.GONE);
            }
            if (!sm.isBehindShowing()) {
                moveTaskToBack(true);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_SSO: {
                if (facebookHandle != null) {
                    facebookHandle.onActivityResult(requestCode, resultCode, data);
                }
                break;
            }
        }
    }

    private void initSearchField() {
        final EditText searchField = (EditText) findViewById(R.id.search_field);
        final EditText searchLocationField = (EditText) findViewById(R.id.search_location_field);
        searchField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchLocationField.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    searchLocationField.setVisibility(View.GONE);
                    searchLocationField.setText(null);
                } else {
                    searchLocationField.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        setOnFocusLostListener(new AbstractActivity.OnFocusLostListener() {
            @Override
            public void onFocusLost(View view) {
                if (TextUtils.isEmpty(searchField.getText()) && TextUtils.isEmpty(searchLocationField.getText())) {
                    searchLocationField.setVisibility(View.GONE);
                    searchLocationField.setText(null);
                }
            }
        });
        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getFragment(pager.getCurrentItem()).loadData();
                    return true;
                }
                return false;
            }
        };
        searchField.setOnEditorActionListener(onEditorActionListener);
        searchLocationField.setOnEditorActionListener(onEditorActionListener);
    }

    private void requestCategories() {
        showProgress(getString(R.string.loading_categories));
        VibeItApiUtils.getCategories(this, categoriesReceiver);
    }

    private void initFragments() {
        fragments = new AbstractFragment[4];
        fragments[0] = LocalVibesFragment.newInstance();
        fragments[1] = TopPerformersFragment.newInstace();
        fragments[2] = RisingStarsFragment.newInstance();
        fragments[3] = MyVibesFragment.newInstance();
    }

    private void initSideMenu() {
        setBehindLeftContentView(R.layout.vibes_filter);
        setBehindRightContentView(R.layout.account_settings);
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow_left, SlidingMenu.LEFT);
        sm.setShadowDrawable(R.drawable.shadow_right, SlidingMenu.RIGHT);
        sm.setBehindOffsetRes(R.dimen.actionbar_home_width, SlidingMenu.BOTH);
        sm.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                if (getFragment(pager.getCurrentItem()).isPrefsChanged()) {
                    getFragment(pager.getCurrentItem()).loadData();
                    getFragment(pager.getCurrentItem()).setPrefsChanged(false);
                }
            }
        });

        View.OnClickListener toggleSideMenu = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_filter:
                        toggle(SlidingMenu.LEFT);
                        break;
                    case R.id.btn_profile:
                        toggle(SlidingMenu.RIGHT);
                        break;
                }
            }
        };
        aq.id(R.id.btn_filter).clicked(toggleSideMenu);
        aq.id(R.id.btn_profile).clicked(toggleSideMenu);
    }

    private void setUpSideMenu() {
        if (getFragment(pager.getCurrentItem()).hasMenu()) {
            aq.id(R.id.btn_filter).visible();
            setBehindLeftContentView(getFragment(pager.getCurrentItem()).getMenu());
        } else {
            aq.id(R.id.btn_filter).gone();
        }
    }

    private void initViewPager() {
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new HomeScreenPagerAdapter(getSupportFragmentManager(), fragments));
        pager.setPageMargin(1);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                fixedTabs.selectTab(position);
                getFragment(pager.getCurrentItem()).loadData();
                setUpSideMenu();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


    }

    private void initActionButtons() {
        aq.id(R.id.btn_log_in).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.signIn(HomeScreen.this);
            }
        });

        if (getApp().isLoggedIn()) {
            aq.id(R.id.btn_log_in).gone();
            aq.id(R.id.btn_profile).visible();
        } else {
            aq.id(R.id.btn_log_in).visible();
            aq.id(R.id.btn_profile).gone();
        }
    }

    private void initUserSettings() {
        if (getApp().isLoggedIn()) {
            initEditProfileView();
            aq.id(R.id.btn_terms).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            });
            aq.id(R.id.btn_tutorial).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            });
            aq.id(R.id.btn_logout).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
            aq.id(R.id.btn_edit_account).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editProfileView.setVisibility(View.VISIBLE);
                    editProfileView.startAnimation(AnimationUtils.loadAnimation(HomeScreen.this, R.anim.slide_up));
                }
            });
            aq.id(R.id.btn_feedback).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SocialShareUtils.sendFeedback(HomeScreen.this);
                }
            });
            aq.id(R.id.btn_social_share_settings).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View shareSettings = findViewById(R.id.social_share_settinngs_block);
                    ExpandAnimation expandAnimation = new ExpandAnimation(shareSettings, 700);
                    shareSettings.startAnimation(expandAnimation);
                }
            });
            aq.id(R.id.btn_rewards).clicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View rewardsEmpty = findViewById(R.id.rewards_empty);
                    ExpandAnimation expandAnimation = new ExpandAnimation(rewardsEmpty, 700);
                    rewardsEmpty.startAnimation(expandAnimation);
                }
            });

            twitterConnector = TwitterConnector.getInstance(HomeScreen.this, R.drawable.twitter_sharing_toggle, getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret));
            if (twitterConnector.isAuthorized()) {
                aq.id(R.id.twitter_sharing).text(twitterConnector.getTwitterScreenName());
            }
            final SwitchICS shareTwitterToggle = (SwitchICS) findViewById(R.id.share_twitter_toggle);
            shareTwitterToggle.setChecked(!getApp().getMainSharedPrefs().getBoolean(Settings.SETTING_TWITTER_SHARING, false));
            shareTwitterToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        twitterConnector.disconnect();
                        aq.id(R.id.twitter_sharing).text("Twitter Sharing");
                        getApp().getMainSharedPrefs().edit().putBoolean(Settings.SETTING_TWITTER_SHARING, false).commit();
                    } else {
                        twitterConnector.authorize(HomeScreen.this, new Twitter.DialogListener() {
                            @Override
                            public void onComplete(Bundle values) {
                                twitterConnector.getAuthorizeDialogListener().onComplete(values);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        aq.id(R.id.twitter_sharing).text(twitterConnector.getTwitterScreenName());
                                        shareTwitterToggle.setChecked(false);

                                    }
                                });
                                getApp().getMainSharedPrefs().edit().putBoolean(Settings.SETTING_TWITTER_SHARING, true).commit();
                            }

                            @Override
                            public void onTwitterError(TwitterError e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shareTwitterToggle.setChecked(true);
                                    }
                                });
                            }

                            @Override
                            public void onError(DialogError e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shareTwitterToggle.setChecked(true);
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shareTwitterToggle.setChecked(true);
                                    }
                                });
                            }

                            public void onDismiss() {
                                if (!twitterConnector.isAuthorized()) {
                                    shareTwitterToggle.setChecked(true);
                                }
                            }
                        });
                    }
                }
            });

            aq.id(R.id.share_facebook).text(getApp().getMainSharedPrefs().getString(Settings.SETTING_FACEBOOK_USER_NAME, "Facebook Sharing"));
            SwitchICS shareFacebookToggle = (SwitchICS) findViewById(R.id.share_facebook_toggle);
            shareFacebookToggle.setChecked(!getApp().getMainSharedPrefs().getBoolean(Settings.SETTING_FACEBOOK_SHARING, false));
            shareFacebookToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        getApp().getMainSharedPrefs().edit().putBoolean(Settings.SETTING_FACEBOOK_SHARING, false).commit();
                    } else {
                        if (!facebookHandle.authenticated()) {

                            facebookHandle.ajaxProfile(fbProfileCallback);
                        }
                        getApp().getMainSharedPrefs().edit().putBoolean(Settings.SETTING_FACEBOOK_SHARING, true).commit();
                    }
                }
            });
        }
    }

    AjaxCallback<JSONObject> fbProfileCallback = new AjaxCallback<JSONObject>() {
        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
            if (json != null) {
                // success
                String name = json.opt("first_name") + " " + json.optString("last_name");
                getApp().getMainSharedPrefs().edit().putString(Settings.SETTING_FACEBOOK_USER_NAME, name).commit();
                aq.id(R.id.share_facebook).text(name);
            } else {
                // failure
//                Toast.makeText(HomeScreen.this, "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void initEditProfileView() {
        if (editProfileView == null) {
            editProfileView = getLayoutInflater().inflate(R.layout.edit_profile, null);
            editProfileView.setVisibility(View.GONE);
            ((ViewGroup) getWindow().getDecorView()).addView(editProfileView);
            UiUtils.initChains(HomeScreen.this);
        }
        final UserResponsePayload user = getApp().getSavedUserInfo();

        aq.id(R.id.first_name).text(user.getUser().getFirstName());
        aq.id(R.id.last_name).text(user.getUser().getLastName());
        aq.id(R.id.email).text(user.getUser().getEmail());

        aq.id(R.id.btn_continue).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(getString(R.string.please_wait));
                VibeItApiUtils.editProfile(HomeScreen.this, editProfileReceiver, aq.id(R.id.first_name).getText().toString(), aq.id(R.id.last_name).getText().toString(), aq.id(R.id.email).getText().toString(), aq.id(R.id.password).getText().toString(), aq.id(R.id.password_confirm).getText().toString(), user.getUserToken().getToken());
            }
        });

        aq.id(R.id.btn_close).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileView.startAnimation(AnimationUtils.loadAnimation(HomeScreen.this, R.anim.slide_down));
                editProfileView.setVisibility(View.GONE);
            }
        });
    }

    public SearchArgs getSearchArgs() {
        SearchArgs searchArgs = new SearchArgs();
        EditText searchInput = (EditText) findViewById(R.id.search_field);
        searchArgs.setQuery(searchInput != null ? searchInput.getText().toString().trim() : "");

        EditText searchLocationInput = (EditText) findViewById(R.id.search_location_field);
        searchArgs.setAddress(searchLocationInput != null ? searchLocationInput.getText().toString().trim() : "");
        return searchArgs;
    }

    private void logout() {
        new Thread() {
            public void run() {
                LocalPersistStorage.deleteFile(HomeScreen.this, Settings.USER_INFO);
                facebookHandle.unauth();
                twitterConnector.disconnect();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toggle(SlidingMenu.RIGHT);
                        initActionButtons();
                        getFragment(pager.getCurrentItem()).loadData();
                    }
                });
            }
        }.start();
    }

    private AbstractFragment getFragment(int position) {
        AbstractFragment f = savedInstanceState == null ? fragments[position] : (AbstractFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
        return savedInstanceState == null ? fragments[position] : (AbstractFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.pager + ":" + position;
    }
}
