package com.example.qapitalinterview.profile;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.entity.Utils;

public class GoalDetailsTitleBehaviour extends CoordinatorLayout.Behavior<View> {

    private TextView goalTitle;
    private TextView goalCash;
    private View goalProgress;

    private TextView goalTitleFinal;
    private TextView goalCashFinal;

    private int startTitleX;
    private int startTitleY;
    private int finalTitleX;
    private int finalTitleY;
    private int startTitleFont;
    private int finalTitleFont;

    private int startGoalX;
    private int startGoalY;
    private int finalGoalX;
    private int finalGoalY;
    private int startGoalFont;
    private int finalGoalFont;

    private int progressStartX;
    private int progressStartY;
    private int progressFinalX;
    private int progressFinalY;

    private int maxScrollingOffset;
    private int actionBarHeight;
    private int progressThreshold;

    private int progressDelta;

    private Decorator decorator;

    public GoalDetailsTitleBehaviour() {
        decorator = new Decorator();
    }

    public GoalDetailsTitleBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
        decorator = new Decorator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        boolean isDependencyChange = dependency instanceof Toolbar || dependency instanceof android.support.v7.widget.Toolbar;
        return isDependencyChange;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        init(parent, child, dependency);
        decorator.applyNewPosition(child, dependency);
        decorator.applyNewFontSize(child, dependency);
        decorator.applyEffects(child, dependency);
        return true;
    }

    private void init(View parent, View child, View dependency) {
        Resources res = child.getContext().getResources();
        if (goalTitle == null) {
            goalTitle = (TextView) child.findViewById(R.id.goal_progress_title);
            goalCash = (TextView) child.findViewById(R.id.goal_progress_cash);
            goalProgress = child.findViewById(R.id.goal_progress);
            goalTitleFinal = (TextView) parent.findViewById(R.id.goal_progress_final);
            goalCashFinal = (TextView) parent.findViewById(R.id.goal_progress_cash_final);

            maxScrollingOffset = (int) dependency.getY();
            actionBarHeight = getToolBarHeight(child.getContext());
            progressThreshold = actionBarHeight - Utils.getStatusBarHeight(res);

            Log.d(App.TAG, "Status bar height inside view behavior: " + Utils.getStatusBarHeight(res));

            startTitleX = (int) goalTitle.getX();
            startTitleY = (int) goalTitle.getY();
            finalTitleX = res.getDimensionPixelSize(R.dimen.goal_title_final_x);
            finalTitleY = res.getDimensionPixelSize(R.dimen.goal_title_final_y);
            startTitleFont = (int) res.getDimension(R.dimen.goal_title_font_origin);
            finalTitleFont = (int) res.getDimension(R.dimen.goal_title_font_final);

            startGoalX = (int) goalCash.getX();
            startGoalY = (int) goalCash.getY();
            finalGoalX = res.getDimensionPixelSize(R.dimen.goal_cash_final_x);
            finalGoalY = res.getDimensionPixelSize(R.dimen.goal_cash_final_y);
            startGoalFont = (int) res.getDimension(R.dimen.goal_cash_font_origin);
            finalGoalFont = (int) res.getDimension(R.dimen.goal_cash_font_final);

            progressStartX = (int) goalProgress.getX();
            progressStartY = (int) goalProgress.getY();
            progressFinalX = res.getDimensionPixelSize(R.dimen.goal_progress_final_x);
            progressFinalY = res.getDimensionPixelSize(R.dimen.goal_progress_final_y);

            progressDelta = progressStartY - startGoalY;
        }
    }

    public int getToolBarHeight(Context context) {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }

    private float getScaledPixels(Resources res, float pixel) {
        float density = res.getDisplayMetrics().density;
        return (pixel / density);
    }

    private class Decorator {

        public void applyNewPosition(View child, View dependency) {
            goalTitle.setY(getMovementYTotal(dependency));
            goalTitle.setX(getMovementXTotal(dependency));
            goalCash.setX(getMovementXGoal(dependency));
            goalCash.setY(getMovementYGoal(dependency));
            goalProgress.setX(getMovementXProgress(dependency));
            goalProgress.setY(getMovementYProgress(dependency));
        }

        public void applyNewFontSize(View child, View dependency) {
            Resources res = child.getResources();
            goalTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, getScaledPixels(res, getFontSizeTitle(dependency)));
            goalCash.setTextSize(TypedValue.COMPLEX_UNIT_SP, getScaledPixels(res, getFontSizeCash(dependency)));
        }

        private int getFontSizeTitle(View dependency) {
            final int deltaFontSize = startTitleFont - finalTitleFont;
            return (int) (finalTitleFont + deltaFontSize * getScrollingRatio(dependency));
        }

        private int getFontSizeCash(View dependency) {
            final int deltaFontSize = startGoalFont - finalGoalFont;
            return (int) (finalGoalFont + deltaFontSize * getScrollingRatio(dependency));
        }

        private int getMovementXTotal(View dependency) {
            final int deltaX = startTitleX - finalTitleX;
            return (int) (finalTitleX + deltaX * getScrollingRatio(dependency));
        }

        private int getMovementYTotal(View dependency) {
            final int deltaY = startTitleY - finalTitleY;
            return (int) (finalTitleY + deltaY * getScrollingRatio(dependency));
        }

        private int getMovementXGoal(View dependency) {
            final int deltaX = startGoalX - finalGoalX;
            return (int) (finalGoalX + deltaX * getScrollingRatio(dependency));
        }

        private int getMovementYGoal(View dependency) {
            final int deltaY = startGoalY - finalGoalY;
            return (int) (finalGoalY + deltaY * getScrollingRatio(dependency));
        }

        private int getMovementXProgress(View dependency) {
            final int deltaX = progressStartX - progressFinalX;
            return (int) (progressFinalX + deltaX * getScrollingRatio(dependency));
        }

        private int getMovementYProgress(View dependency) {
//            final int deltaY = progressStartY - progressFinalY;
//            return (int) (progressFinalY + deltaY * getScrollingRatio(dependency));
            return (int) (goalCash.getY() + progressDelta);
        }

        private float getScrollingRatio(View dependency) {
            return dependency.getY() / maxScrollingOffset;
        }

        private float getAlpha(View dependency) {
            if (dependency.getY() > progressThreshold) return 1;
            // due conditions above code below would be called only for movement within small delta
            return dependency.getY() / progressThreshold;
        }

        public void applyEffects(View child, View dependency) {
            // apply effects
            goalProgress.setAlpha(getAlpha(dependency));
            boolean isScrollingItemVisible = dependency.getY() > 0;
            goalTitle.setVisibility(isScrollingItemVisible ? View.VISIBLE : View.INVISIBLE);
            goalCash.setVisibility(isScrollingItemVisible ? View.VISIBLE : View.INVISIBLE);
            goalTitleFinal.setVisibility(isScrollingItemVisible ? View.INVISIBLE : View.VISIBLE);
            goalCashFinal.setVisibility(isScrollingItemVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
