package com.example.qapitalinterview;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.qapitalinterview.interactor.GoalDetailsInteractor;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ElapsedTimeInstrumenterdTest {

    @Test
    public void testElapsedTimeMinutes() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1479639558000L, context);

        assertEquals("6 minutes ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeHour() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1479643156000L, context);

        assertEquals("1 hour ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeHours() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1479646406000L, context);

        assertEquals("2 hours ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeDay() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1479729556000L, context);

        assertEquals("1 day ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeDays() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1479902356000L, context);

        assertEquals("3 days ago", elapsedTime);
    }


    @Test
    public void testElapsedTimeMonth() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1482494356000L, context);

        assertEquals("1 month ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeMonths() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1485003556000L, context);

        assertEquals("2 months ago", elapsedTime);
    }


    @Test
    public void testElapsedTimeYear() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1516539556000L, context);

        assertEquals("1 year 2 months ago", elapsedTime);
    }

    @Test
    public void testElapsedTimeYears() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String elapsedTime = interactor.getElapsedTime(1479639196952L, 1548075556000L, context);

        assertEquals("2 years 2 months ago", elapsedTime);
    }
}
