package com.vibeit.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.androidquery.AQuery;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityBase;
import com.slidingmenu.lib.app.SlidingActivityHelper;
import com.vibeit.app.VibeItApplication;
import com.vibeit.dialog.VibeItProgressDialog;

/**
 * @author Maria Dzyokh
 */
public class AbstractSlidingFragmentActivity extends FragmentActivity implements SlidingActivityBase {

    protected VibeItProgressDialog progressDialog;
    protected AQuery aq;

    private AbstractActivity.OnFocusLostListener focusLostListener;
    private SlidingActivityHelper slidingHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidingHelper = new SlidingActivityHelper(this);
        slidingHelper.onCreate(savedInstanceState);
        aq = new AQuery(this);
        progressDialog = new VibeItProgressDialog(this, "Please Wait ...");
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        slidingHelper.onPostCreate(savedInstanceState);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return slidingHelper.findViewById(id);
    }

    @Override
    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    @Override
    public void setContentView(View v) {
        setContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View v, ViewGroup.LayoutParams params) {
        super.setContentView(v, params);
        slidingHelper.registerAboveContentView(v, params);
    }

    // behind left view
    public void setBehindLeftContentView(int id) {
        setBehindLeftContentView(getLayoutInflater().inflate(id, null));
    }

    public void setBehindLeftContentView(View v) {
        setBehindLeftContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setBehindLeftContentView(View v, ViewGroup.LayoutParams params) {
        slidingHelper.setBehindLeftContentView(v);
    }

    // behind right view
    public void setBehindRightContentView(int id) {
        setBehindRightContentView(getLayoutInflater().inflate(id, null));
    }

    public void setBehindRightContentView(View v) {
        setBehindRightContentView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setBehindRightContentView(View v, ViewGroup.LayoutParams params) {
        slidingHelper.setBehindRightContentView(v);
    }

    public SlidingMenu getSlidingMenu() {
        return slidingHelper.getSlidingMenu();
    }

    public void toggle(int side) {
        slidingHelper.toggle(side);
    }

    public void showAbove() {
        slidingHelper.showAbove();
    }

    public void showBehind(int side) {
        slidingHelper.showBehind(side);
    }

    public void setSlidingActionBarEnabled(boolean b) {
        slidingHelper.setSlidingActionBarEnabled(b);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = slidingHelper.onKeyUp(keyCode, event);
        if (b) return b;
        return super.onKeyUp(keyCode, event);
    }

    protected VibeItApplication getApp() {
        return (VibeItApplication) getApplication();
    }

    protected void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        // hide soft keyboard when user touches anywhere outside edittext
        if (v != null && v instanceof EditText) {
            int locationOnScreen[] = new int[2];
            v.getLocationOnScreen(locationOnScreen);
            float x = event.getRawX() + v.getLeft() - locationOnScreen[0];
            float y = event.getRawY() + v.getTop() - locationOnScreen[1];

            if (event.getAction() == MotionEvent.ACTION_UP && (x < v.getLeft() || x >= v.getRight() || y < v.getTop() || y > v.getBottom())) {
                v.post(new Runnable() {
                    @Override
                    public void run() {
                        View newFocusView = getCurrentFocus();
                        if (newFocusView == v || !(newFocusView instanceof EditText)) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

                            if (focusLostListener != null) {
                                focusLostListener.onFocusLost(v);
                            }
                        }
                    }
                });
            }
        }
        return ret;
    }

    public void setOnFocusLostListener(AbstractActivity.OnFocusLostListener focusLostListener) {
        this.focusLostListener = focusLostListener;
    }
}