package com.vibeit.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.cyberwalkabout.common.util.Sys;
import com.vibeit.R;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.intent.IntentHelper;

/**
 * @author Maria Dzyokh
 */
public class MyVibesFragment extends AbstractFragment {

    private RelativeLayout loginPopup;

    public static MyVibesFragment newInstance() {
        return new MyVibesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initLoginPopup(inflater, view);
        return view;
    }

    @Override
    public void loadData(int page, String nextPageToken, boolean showProgress) {
        android.location.Location lastKnownLocation = Sys.getLatestKnownLocation(getActivity());
        if (lastKnownLocation != null) {
            if (TextUtils.isEmpty(userToken)) {
                showLoginPopup();
            } else {
                if (showProgress) {
                    showProgress(getString(R.string.loading_locations));
                }
                VibeItApiUtils.getMyVibes(getActivity(), locationsReceiver, userToken, getSearchArgs(), page, nextPageToken);
            }
        } else {
                aq.id(android.R.id.empty).text("Unable to determine your location");
        }
    }

    @Override
    public boolean hasMenu() {
        return false;
    }

    @Override
    public View getMenu() {
        return null;
    }

    private void showLoginPopup() {
        loginPopup.setVisibility(View.VISIBLE);
        loginPopup.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up));
    }

    private void hideLoginPopup() {
        loginPopup.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down));
        loginPopup.setVisibility(View.GONE);
    }

    private void initLoginPopup(LayoutInflater li, View parent) {
        loginPopup = (RelativeLayout) li.inflate(R.layout.login_popup, null);
        ((RelativeLayout)parent).addView(loginPopup);
        loginPopup.findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentHelper.signIn(getActivity());
            }
        });
        loginPopup.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               hideLoginPopup();
            }
        });
        loginPopup.setVisibility(View.GONE);

    }
}
