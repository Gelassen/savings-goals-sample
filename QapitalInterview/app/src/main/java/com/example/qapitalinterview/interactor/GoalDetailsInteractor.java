package com.example.qapitalinterview.interactor;


import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.entity.FilterManager;
import com.example.qapitalinterview.model.SavingsRule;

public class GoalDetailsInteractor {

    public Spanned getAchievement(String text) {
        return Html.fromHtml(text);
    }

    public String getElapsedTime(long time, Context context) {
        final long current = System.currentTimeMillis();
        final long elapsed = current - time;

        long seconds = elapsed / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long monthes = days / 30;// ~ aproximate numbers in month
        long years = monthes / 12;

        StringBuilder result = new StringBuilder();
        if (years != 0) {
            result.append(years);
            result.append(" ");
            result.append(context.getString(R.string.elapsed_years));
            result.append(" ");
        }
        if (monthes != 0) {
            result.append(monthes);
            result.append(" ");
            result.append(context.getString(R.string.elapsed_months));
            result.append(" ");
        }
        if (days != 0) {
            result.append(days);
            result.append(" ");
            result.append(context.getString(R.string.elapsed_days));
            result.append(" ");
        }
        if (hours != 0) {
            result.append(hours);
            result.append(" ");
            result.append(context.getString(R.string.elapsed_hours));
            result.append(" ");
        }
        if (minutes != 0) {
            result.append(minutes);
            result.append(" ");
            result.append(context.getString(R.string.elapsed_minutes));
            result.append(" ");
        }
        result.append(seconds);
        result.append(" ");
        result.append(context.getString(R.string.elapsed_seconds));
        result.append(" ");
        result.append("ago");

        return result.toString();
    }

    public String getBalance(double amount) {
        return String.format("$%.2f", amount);
    }

    public SavingsRule getAllItemsFilter() {
        return new FilterManager().getAllItemsFilter();
    }
}
