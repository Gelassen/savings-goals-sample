package com.example.qapitalinterview.entity;


public class ElapsedTime {
    private static final long TIME_YEARS_MLS = 31104000000L;
    private static final long TIME_MONTHS_MLS = TIME_YEARS_MLS / 12;
    private static final long TIME_DAYS_MLS = TIME_MONTHS_MLS / 30;
    private static final long TIME_HOURS_MLS = TIME_DAYS_MLS / 24;
    private static final long TIME_MINUTES_MLS = TIME_HOURS_MLS / 60;

    private long time;

    private long years;
    private long months;
    private long days;
    private long hours;
    private long minutes;

    public void setTime(long time) {
        this.time = time;
    }

    public void calculate() {
        years = time / TIME_YEARS_MLS;
        long rest = time % TIME_YEARS_MLS;

        months = rest / TIME_MONTHS_MLS;
        rest %= TIME_MONTHS_MLS;

        days = rest / TIME_DAYS_MLS;
        rest %= TIME_DAYS_MLS;

        hours = rest / TIME_HOURS_MLS;
        rest %= TIME_HOURS_MLS;

        minutes = rest / TIME_MINUTES_MLS;
    }

    public long getYears() {
        return years;
    }

    public long getMonths() {
        return months;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }
}
