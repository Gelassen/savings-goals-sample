package com.example.qapitalinterview;


import android.util.Log;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.utils.TestUtils;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

public class ApiTest extends BaseTest{

    private MockWebServer webServer;

    @Inject
    IApi api;

    private TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        Log.d(App.TAG, "Test setUp");
        super.setUp();
        component.inject(this);

        testUtils = new TestUtils();
        webServer = new MockWebServer();
        webServer.start();
        webServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                MockResponse response;
                if (request.getPath().equals("/savingsgoals")) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/goals.json"));
                } else if (request.getPath().equals("/savingsgoals/" + TestApp.Const.GOAL_ID + "/feed")) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/feed.json"));
                } else if (request.getPath().equals("/savingsrules")) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/rules.json"));
                } else if (request.getPath().equals("/user/" + TestApp.Const.USER_ID)) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/user.json"));
                } else {
                    response = new MockResponse().setResponseCode(404);
                }
                return response;
            }
        });
        HttpUrl url = webServer.url("/");
        api = ApiModule.getApiInterface(url.toString());
    }

    @Test
    public void testGoals() throws Exception {
        TestSubscriber<SavingsGoals> testSubscriber = new TestSubscriber<>();
        api.getSavingsGoals().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SavingsGoals goals = testSubscriber.getOnNextEvents().get(0);
        goals.getSavingsGoals();

        SavingsGoal savingsGoal = goals.getSavingsGoals().get(0);

        assertEquals(
                "https://static.qapitalapp.net/assets/ios-staging.api.qapital.com/images/goal/6dc5befb-5389-4d89-ab37-205c83ccf79c.jpg",
                savingsGoal.getGoalImageURL()
        );
        assertEquals(Integer.valueOf(1), savingsGoal.getUserId());
        assertEquals(Integer.valueOf(1), savingsGoal.getId());
        assertEquals(Float.valueOf(1500.0f), savingsGoal.getTargetAmount());
        assertEquals(Integer.valueOf(400), savingsGoal.getCurrentBalance());
        assertEquals("active", savingsGoal.getStatus());
        assertEquals("Balloons", savingsGoal.getName());
        assertEquals(8, goals.getSavingsGoals().size());
    }

    @Test
    public void testSavingsRules() throws Exception {
        TestSubscriber<SavingRules> testSubscriber = new TestSubscriber<>();
        api.getSavingsRules().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SavingRules savingRules = testSubscriber.getOnNextEvents().get(0);
        assertEquals(2, savingRules.getSavingsRules().size());

        SavingsRule rule = savingRules.getSavingsRules().get(0);
        assertEquals(Integer.valueOf(1), rule.getId());
        assertEquals("roundup", rule.getType());
        assertEquals(Integer.valueOf(1), rule.getAmount());
    }

    @Test
    public void test() throws Exception {
        TestSubscriber<Feeds> testSubscriber = new TestSubscriber<>();
        api.getSavingsGoalFeed(TestApp.Const.GOAL_ID).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        Feeds feeds = testSubscriber.getOnNextEvents().get(0);
        assertEquals(8, feeds.getFeed().size());

        Feed feed = feeds.getFeed().get(0);
        assertEquals("62e83994-32b5-4305-97e6-f6a0a8e3013a", feed.getId());
        assertEquals("saving", feed.getType());
        assertEquals("2015-03-10T14:55:16.025Z", feed.getTimestamp());
        assertEquals(0, feed.getTime());
        assertEquals("<strong>You</strong> made a manual transfer.", feed.getMessage());
        assertEquals(Double.valueOf(10.0), feed.getAmount());
        assertEquals(Integer.valueOf(1), feed.getUserId());
        assertEquals(0, feed.getGoalId());
    }
}
