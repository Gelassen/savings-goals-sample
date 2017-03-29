package com.example.qapitalinterview.profile;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;

@SuppressWarnings("unused")
public class CustomViewBehaviour extends CoordinatorLayout.Behavior<ImageView> {

    private Context context;
    private int maxScrollingOffset;
    private int smallX;
    private int smallY;

    private int originX;
    private int originY;

    private Decorator decorator;

    public CustomViewBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.decorator = new Decorator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof Toolbar || dependency instanceof android.widget.Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        // TODO onDependentViewChanged
        initPropertiesIfNeeded(child, dependency);

        decorator.addVerticalOffset(child, dependency);
        decorator.addHorizontalOffset(child, dependency);
        decorator.applyResize(child, dependency);
        decorator.getStatusBarHeight();

        child.requestLayout();

        Log.d(App.TAG, "Image position: " + "X: " + child.getX() + " Y: " + child.getY());

        return true;//super.onDependentViewChanged(parent, child, dependency);
    }


    private boolean triggerThreshold(View dependency) {
        return dependency.getY() <= getStatusBarHeight();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initPropertiesIfNeeded(ImageView child, View dependency) {
        if (maxScrollingOffset == 0) {
            maxScrollingOffset = (int) dependency.getY();
        }
        if (originX == 0) {
            originX = (int) child.getX();
        }
        if (originY == 0) {
            originY = (int) child.getY();
        }
        if (smallX == 0) {
            smallX = context.getResources()
                    .getDimensionPixelSize(R.dimen.avatar_small_margin_left);
        }
        if (smallY == 0) {
            smallY = context.getResources()
                    .getDimensionPixelSize(R.dimen.avatar_small_width_height) / 2;
        }
    }

    private class Decorator {

        public void addVerticalOffset(View child, View dependency) {
            final int margin = context.getResources()
                    .getDimensionPixelSize(R.dimen.avatar_top_margin);
            child.setY((int) dependency.getY() + margin);
        }

        public void addHorizontalOffset(View child, View dependency) {
            final int delta = originX - smallX;
            child.setX(smallX + (int) (delta * getScrollingRatio(dependency)));
        }

        public void applyResize(View child, View dependency) {
            // TODO apply resize
            final float viewRatio = (float) getFinalHeight() / getOriginHeight();
            final float deltaHeight = getOriginHeight() - getFinalHeight();
            final float deltaWidth = getOriginWidth() - getFinalWidth();

            child.getLayoutParams().height = getFinalHeight() + (int) (deltaHeight * getScrollingRatio(dependency));
            child.getLayoutParams().width = getFinalWidth() + (int) (deltaWidth * getScrollingRatio(dependency));

            // calculate position offset
            child.setX(getMovementX(child, dependency));
            child.setY(getMovementY(child, dependency));
        }

        public int getStatusBarHeight() {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        private int getOriginWidth() {
            return context.getResources().getDimensionPixelSize(R.dimen.avatar_width_height);
        }

        private int getOriginHeight() {
            return context.getResources().getDimensionPixelSize(R.dimen.avatar_width_height);
        }

        private int getFinalWidth() {
            return context.getResources().getDimensionPixelSize(R.dimen.avatar_small_width_height);
        }

        private int getFinalHeight() {
            return context.getResources().getDimensionPixelSize(R.dimen.avatar_small_width_height);
        }

        private float getScrollingRatio(View dependency) {
            return dependency.getY() / maxScrollingOffset;
        }

        private int getMovementX(View child, View dependency) {
            // find minimum offset for final position and keep it persistent
            // calculate the delta with current position and apply ratio
            final int finalPosition = context.getResources().getDimensionPixelSize(R.dimen.avatar_small_margin_left);
            final int delta = (int) child.getX() - finalPosition;
            return (int) (finalPosition + delta * getScrollingRatio(dependency));
        }

        private int getMovementY(View child, View dependency) {
            final int finalPosition = context.getResources().getDimensionPixelSize(R.dimen.avatar_small_margin_top);
            final int delta = (int) (child.getY() - finalPosition) + getStatusBarHeight();
            return (int) (finalPosition + delta * getScrollingRatio(dependency));
        }

    }

}
