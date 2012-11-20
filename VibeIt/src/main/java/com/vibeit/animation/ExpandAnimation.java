package com.vibeit.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * This animation class is animating the expanding and reducing the size of a view.
 * The animation toggles between the Expand and Reduce, depending on the current state of the view
 */
public class ExpandAnimation extends Animation {
    private View animatedView;
    private LinearLayout.LayoutParams viewLayoutParams;
    private int marginStart, marginEnd;
    private boolean isVisibleAfter = false;
    private boolean wasEndedAlready = false;

    /**
     * Initialize the animation
     *
     * @param view     The layout we want to animate
     * @param duration The duration of the animation, in ms
     */
    public ExpandAnimation(View view, int duration) {

        setDuration(duration);
        animatedView = view;
        viewLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();

        // decide to show or hide the view
        isVisibleAfter = (view.getVisibility() == View.VISIBLE);
        marginStart = viewLayoutParams.bottomMargin;
        marginEnd = (marginStart == 0 ? (0 - view.getHeight()) : 0);

        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        if (interpolatedTime < 1.0f) {

            // Calculating the new bottom margin, and setting it
            viewLayoutParams.bottomMargin = marginStart
                    + (int) ((marginEnd - marginStart) * interpolatedTime);

            // Invalidating the layout, making us seeing the changes we made
            animatedView.requestLayout();

            // Making sure we didn't run the ending before (it happens!)
        } else if (!wasEndedAlready) {
            viewLayoutParams.bottomMargin = marginEnd;
            animatedView.requestLayout();

            if (isVisibleAfter) {
                animatedView.setVisibility(View.GONE);
            }
            wasEndedAlready = true;
        }
    }
}
