package com.example.qapitalinterview.interactor;


import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.entity.ElapsedTime;
import com.example.qapitalinterview.entity.ElapsedTimeFormatter;
import com.example.qapitalinterview.entity.FilterManager;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;

import org.joda.time.Period;

import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class GoalDetailsInteractor {

    public Spanned getAchievement(String text) {
        return Html.fromHtml(text);
    }

    public String getElapsedTime(final long time, Context context) {
        Period period = new Period(time, System.currentTimeMillis());
        ElapsedTimeFormatter formatter = new ElapsedTimeFormatter(period);
        return formatter.prepareTime(context);
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
        String target = isTargetNotSpecified ? context.getString(R.string.placeholder_unknown_target)
                : String.valueOf(goal.getTargetAmount().intValue());
        return String.format(isTargetNotSpecified ? notSpecifiedTargetFormat : formmater, goal.getCurrentBalance(), target);
    }

    public String getBalance(double amount) {
        return String.format("$%.2f", amount);
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
        int balance = 0;
        for (Feed feed : feeds) {
            balance += feed.getAmount();
        }
        return String.format("This week : %s", balance);
    }

    public List<Boolean> updateFilterModel(final int selectedFilterPos, List<Boolean> filterModel) {
        for (int idx = 0; idx < filterModel.size(); idx++) {
            boolean selected = idx == selectedFilterPos;
            filterModel.set(idx, selected);
        }
        return filterModel;
    }
}
