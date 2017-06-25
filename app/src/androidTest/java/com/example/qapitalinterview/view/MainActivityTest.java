package com.example.qapitalinterview.view;


import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.idling.ListIdlingResource;
import com.example.qapitalinterview.robots.GoalsRobot;
import com.example.qapitalinterview.utils.Params;
import com.example.qapitalinterview.utils.TestUtils;
import com.example.qapitalinterview.di.ApiModule;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IApi api;

    private TestUtils testUtils;
    private MockWebServer webServer;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private ListIdlingResource listIdlingResource;

    @Before
    public void setUp() throws Exception {
        this.listIdlingResource = new ListIdlingResource(mActivityTestRule.getActivity());
        Espresso.registerIdlingResources(listIdlingResource);
        Intents.init();

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
                } else if (request.getPath().equals("/savingsgoals/" + Params.Const.GOAL_ID + "/feed")) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/feed.json"));
                } else if (request.getPath().equals("/savingsrules")) {
                    response = new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/rules.json"));
                } else if (request.getPath().equals("/user/" + Params.Const.USER_ID)) {
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

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(listIdlingResource);
        Intents.release();
    }

    @Test
    public void mainActivityTest() {
        new GoalsRobot()
                .seesGoalsList()
                .clickSecondItem()
                .seesGoalDetailsActivity(mActivityTestRule.getActivity());
    }

}
