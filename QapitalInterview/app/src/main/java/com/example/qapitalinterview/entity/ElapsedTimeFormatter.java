package com.example.qapitalinterview.entity;


import android.content.Context;

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
        StringBuilder result = new StringBuilder();
        result.append(period.getYears());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_years));
        result.append(" ");

        result.append(period.getMonths());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_months));
        return result.toString();
    }

    private String getFormatForMonths(Context context) {
        StringBuilder result = new StringBuilder();
        result.append(period.getMonths());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_months));
        return result.toString();
    }

    private String getFormatForDays(Context context) {
        StringBuilder result = new StringBuilder();
        result.append(period.getDays());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_days));
        return result.toString();
    }

    private String getFormatForHours(Context context) {
        StringBuilder result = new StringBuilder();
        result.append(period.getHours());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_hours));
        return result.toString();
    }

    private String getFormatForMinutes(Context context) {
        StringBuilder result = new StringBuilder();
        result.append(period.getHours());
        result.append(" ");
        result.append(context.getString(R.string.elapsed_hours));
        return result.toString();
    }

    private boolean isNotEmpty(long value) {
        return value != 0;
    }
}
