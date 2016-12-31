package com.example.qapitalinterview;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BalanceInstrumentedTest {

    @Test
    public void testBalance() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String balance = interactor.getBalance(10.2);
        Assert.assertEquals("$10.20", balance);
    }

    @Test
    public void testTargetBalance() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        Context context = InstrumentationRegistry.getTargetContext();

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setCurrentBalance(7);
        savingsGoal.setTargetAmount(8f);
        String balance = interactor.getTargetBalance(context, savingsGoal);

        assertEquals("$7 of 8", balance);
    }

    @Test
    public void testTargetBalanceUnknown() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        Context context = InstrumentationRegistry.getTargetContext();

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setCurrentBalance(7);
        savingsGoal.setTargetAmount(null);
        String balance = interactor.getTargetBalance(context, savingsGoal);

        assertEquals("$7 of Unknown", balance);
    }


    @Test
    public void testExtendedTargetBalance() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        Context context = InstrumentationRegistry.getTargetContext();

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setCurrentBalance(7);
        savingsGoal.setTargetAmount(8.8f);
        String balance = interactor.getExtendedTargetBalance(context, savingsGoal);

        assertEquals("$7 of $9", balance);
    }

    @Test
    public void testExtendedTargetBalanceUnknown() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        Context context = InstrumentationRegistry.getTargetContext();

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setCurrentBalance(7);
        savingsGoal.setTargetAmount(null);
        String balance = interactor.getExtendedTargetBalance(context, savingsGoal);

        assertEquals("$7 of Unknown", balance);
    }

    @Test
    public void testAchievements() throws Exception {
        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        List<Feed> feeds = new ArrayList<>();
        Feed feed1 = new Feed();
        feed1.setAmount(10.6);
        feeds.add(feed1);

        Feed feed2 = new Feed();
        feed2.setAmount(3.3);
        feeds.add(feed2);

        Feed feed3 = new Feed();
        feed3.setAmount(4.5);
        feeds.add(feed3);

        String balanceForThisWeek = interactor.getAchievements(feeds);
        assertEquals("This week : 18.4", balanceForThisWeek);
    }

    @Test
    public void testTargetBalance400Of1500() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        SavingsGoal goal = new SavingsGoal();
        goal.setCurrentBalance(400);
        goal.setTargetAmount(1500f);

        GoalDetailsInteractor interactor = new GoalDetailsInteractor();
        String string = interactor.getTargetBalance(context, goal);

        assertEquals("$400 of 1500", string);
    }
}
