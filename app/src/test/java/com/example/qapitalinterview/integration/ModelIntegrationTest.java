package com.example.qapitalinterview.integration;


import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.utils.Params;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

public class ModelIntegrationTest extends IntegrationBaseTest {

    @Inject
    IApi api;

    @Inject
    IModel model;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
    }

    @Test
    public void testInjectedClasses() throws Exception {
        assertNotNull(api);
        assertNotNull(model);
    }

    @Test
    public void testSavingGoals() throws Exception {
        TestSubscriber<SavingsGoals> testSubscriber = new TestSubscriber<>();
        api.getSavingsGoals().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SavingsGoals goals = testSubscriber.getOnNextEvents().get(0);
        assertEquals(8, goals.getSavingsGoals().size());
    }

    @Test
    public void testSavingsGoalFeed() throws Exception {
        TestSubscriber<Feeds> testSubscriber = new TestSubscriber<>();
        api.getSavingsGoalFeed(Params.Const.GOAL_ID).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        Feeds feeds = testSubscriber.getOnNextEvents().get(0);
        assertEquals(8, feeds.getFeed().size());

    }

    @Test
    public void testSavingRules() throws Exception {
        TestSubscriber<SavingRules> testSubscriber = new TestSubscriber<>();
        api.getSavingsRules().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SavingRules rules = testSubscriber.getOnNextEvents().get(0);
        assertEquals(2, rules.getSavingsRules().size());
    }

}
