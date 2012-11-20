package com.vibeit.screen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.androidquery.AQuery;
import com.vibeit.app.VibeItApplication;
import com.vibeit.dialog.VibeItProgressDialog;

/**
 * @author Maria Dzyokh
 */
public abstract class AbstractActivity extends Activity {

    public static final String ACTION_KEYBOARD_HIDDEN = "ACTION_KEYBOARD_HIDDEN";

    protected AQuery aq;
    protected Dialog progressDialog;

    private OnFocusLostListener focusLostListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(this);
        progressDialog = new VibeItProgressDialog(this, "Please Wait ...");
    }

    protected VibeItApplication getApp() {
        return (VibeItApplication) getApplication();
    }

    protected void showProgress() {
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
                        }
                    }
                });

                if (focusLostListener != null) {
                    focusLostListener.onFocusLost(v);
                }

                sendBroadcast(new Intent(ACTION_KEYBOARD_HIDDEN));
            }
        }
        return ret;
    }

    public void setOnFocusLostListener(OnFocusLostListener focusLostListener) {
        this.focusLostListener = focusLostListener;
    }

    public interface OnFocusLostListener {
        void onFocusLost(View view);
    }
}