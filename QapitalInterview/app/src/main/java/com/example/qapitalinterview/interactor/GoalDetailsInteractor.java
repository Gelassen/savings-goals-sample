package com.example.qapitalinterview.interactor;


import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.text.Html;
import android.text.Spanned;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.entity.ElapsedTimeFormatter;
import com.example.qapitalinterview.entity.FilterManager;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;

import org.joda.time.Period;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GoalDetailsInteractor {

    public Spanned getAchievement(String text) {
        return Html.fromHtml(text);
    }

    public String getElapsedTime(final long time, final long currentTime, Context context) {
        Period period = new Period(time, currentTime);
        ElapsedTimeFormatter formatter = new ElapsedTimeFormatter(period);
        return formatter.prepareTime(context);
    }

    public String getElapsedTime(final long time, Context context) {
        return getElapsedTime(time, System.currentTimeMillis(), context);
    }

    public String getTargetBalance(Context context, final SavingsGoal goal) {
        return getFormattedBalance(context, goal, "$%s of %s");
    }

    public String getExtendedTargetBalance(Context context, final SavingsGoal goal) {
        return getFormattedBalance(context, goal, "$%s of $%s");
    }

    private String getFormattedBalance(Context context, final SavingsGoal goal, final String formmater) {
        final String notSpecifiedTargetFormat = "$%s of %s";
        boolean isTargetNotSpecified = goal.getTargetAmount() == null || goal.getTargetAmount() == 0f;
        final String formatted = String.format(isTargetNotSpecified ? notSpecifiedTargetFormat : formmater,
                goal.getCurrentBalance(),
                isTargetNotSpecified ? context.getString(R.string.placeholder_unknown_target) : Math.round(goal.getTargetAmount())
        );

        return formatted;
    }

    /**
     * Traditional string format for case %.2f returns floating point for int float 15 like 15.00.
     * Pre-format float numbers before applying string format
     * */
    @Deprecated
    private String getPrettyFloat(final Float value) {
        String doubleFormat = "%.2f";
        String formattedDouble = String.format(Locale.getDefault(), doubleFormat, value);
        return getPrettyPrint(formattedDouble);
    }

    /**
     * Client supports different representation of numbers with floating points. This method properly chop
     * floating part when number has been formatted
     * */
    @Deprecated
    private String getPrettyPrint(String input) {
        String[] tokens = input.split("\\.");
        String intPart = tokens[0];
        String floatPart = tokens[1];
        char[] lastPart = floatPart.toCharArray();
        boolean isNeedChop = lastPart[0] == '0' && lastPart[1] == '0';

        StringBuilder result = new StringBuilder();
        result.append(intPart);
        if (isNeedChop) {
            // no op
        } else {
            result.append(".");
            result.append(floatPart);
        }

        return result.toString();
    }

    public String getBalance(double amount) {
        return String.format(Locale.getDefault(), "$%.2f", amount);
    }

    public SavingsRule getAllItemsFilter() {
        return new FilterManager().getAllItemsFilter();
    }

    public int getGoalProgress(SavingsGoal goal) {
        double progress = ((double) goal.getCurrentBalance() / (double) goal.getTargetAmount());
        return (int) ((progress) * 100);
    }

    public String getStartOfTheWeek() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return String.valueOf(calendar.getTimeInMillis());
    }

    public String getAchievements(List<Feed> feeds) {
        Double balance = 0d;
        for (Feed feed : feeds) {
            balance += feed.getAmount();
        }
        return String.format("This week : %.1f", balance);
    }

    public List<Boolean> updateFilterModel(final int selectedFilterPos, List<Boolean> filterModel) {
        for (int idx = 0; idx < filterModel.size(); idx++) {
            boolean selected = idx == selectedFilterPos;
            filterModel.set(idx, selected);
        }
        return filterModel;
    }
}
