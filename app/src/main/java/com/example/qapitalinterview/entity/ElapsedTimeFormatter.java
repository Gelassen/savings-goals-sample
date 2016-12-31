package com.example.qapitalinterview.entity;


import android.content.Context;
import android.content.res.Resources;

import com.example.qapitalinterview.R;

import org.joda.time.Period;

public class ElapsedTimeFormatter {

    private Period period;

    public ElapsedTimeFormatter(Period period) {
        this.period = period;
    }

    public String prepareTime(Context context) {
        String result;
        if (isNotEmpty(period.getYears())) {
            result = getFormatForYears(context);
        } else if (isNotEmpty(period.getMonths())) {
            result = getFormatForMonths(context);
        } else if (isNotEmpty(period.getDays())) {
            result = getFormatForDays(context);
        } else if (isNotEmpty(period.getHours())) {
            result = getFormatForHours(context);
        } else {
            result = getFormatForMinutes(context);
        }

        return result;
    }

    private String getFormatForYears(Context context) {
        Resources res = context.getResources();
        StringBuilder result = new StringBuilder();
        result.append(res.getQuantityString(R.plurals.elapsed_years, period.getYears(), period.getYears()));
        result.append(" ");
        result.append(res.getQuantityString(R.plurals.elapsed_months, period.getMonths(), period.getMonths()));
        return result.toString();
    }

    private String getFormatForMonths(Context context) {
        return context.getResources().getQuantityString(R.plurals.elapsed_months, period.getMonths(), period.getMonths());
    }

    private String getFormatForDays(Context context) {
        return context.getResources().getQuantityString(R.plurals.elapsed_days, period.getDays(), period.getDays());
    }

    private String getFormatForHours(Context context) {
        return context.getResources().getQuantityString(R.plurals.elapsed_hours, period.getHours(), period.getHours());
    }

    private String getFormatForMinutes(Context context) {
        return context.getResources().getQuantityString(R.plurals.elapsed_minutes, period.getMinutes(), period.getMinutes());
    }

    private boolean isNotEmpty(long value) {
        return value != 0;
    }
}
