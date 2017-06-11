package com.example.qapitalinterview;


import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.utils.Params;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ModelTest extends BaseTest {

    @Inject
    IApi api;

    private Model model;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        model = new Model(RuntimeEnvironment.application);
    }

    @Test
    public void testApiIsInjected() throws Exception {
        assertFalse(api == null);
    }

    @Test
    public void testApiInModelIsInjected() throws Exception {
        IApi api = model.getApi();
        assertFalse(api == null);
    }

    @Test
    public void testGoalsModel() throws Exception {
        SavingsGoals stubResponse = testUtils.getGson().fromJson(testUtils.readString("json/goals.json"), SavingsGoals.class);
        when(api.getSavingsGoals()).thenReturn(Observable.just(stubResponse));

        TestSubscriber<SavingsGoals> testSubscriber = new TestSubscriber<>();

        Observable<SavingsGoals> goals = model.getSavingGoals();
        goals.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(2);

        SavingsGoals goalsInCache = testSubscriber.getOnNextEvents().get(0);
        assertEquals(0, goalsInCache.getSavingsGoals().size());

        SavingsGoals testGoals = testSubscriber.getOnNextEvents().get(1);
        assertEquals(8, testGoals.getSavingsGoals().size());
    }

    @Test
    public void testSavingsRules() throws Exception {
        // obtain response stub
        SavingRules stubResponse = testUtils.getGson().fromJson(testUtils.readString("json/rules.json"), SavingRules.class);
        when(api.getSavingsRules()).thenReturn(Observable.just(stubResponse));
        // supply testSavingGoals subscriber to the model
        TestSubscriber<SavingRules> testSubscriber = new TestSubscriber<>();
        model.cacheSavingsRules().subscribe(testSubscriber);
        // get response and validate it
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SavingRules rules = testSubscriber.getOnNextEvents().get(0);
        assertEquals(2, rules.getSavingsRules().size());
    }

    @Test
    public void testSavingsGoalFeed() throws Exception {
        // obtain response
        Feeds stubResponse = testUtils.getGson().fromJson(testUtils.readString("json/feed.json"), Feeds.class);
        when(api.getSavingsGoalFeed(Params.Const.GOAL_ID)).thenReturn(Observable.just(stubResponse));
        // supply testSavingGoals subscriber to the model
        TestSubscriber<Feeds> testSubscriber = new TestSubscriber<>();
        model.cacheUserFeeds(Params.Const.GOAL_ID).subscribe(testSubscriber);
        // TODO validate response
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Feeds> result = testSubscriber.getOnNextEvents();
        assertEquals(1, result.size());
        assertEquals(8, result.get(0).getFeed().size());
    }
}
