package com.example.qapitalinterview.webresources;


import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.utils.Params;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;


public class AppServerDispatcher extends Dispatcher {

    private static final int STATUS_OK = 200;

    private String URL_GOALS = "/savingsgoals";
    private String URL_FEED = "/savingsgoals/" + Params.Const.GOAL_ID + "/feed";
    private String URL_RULES = "/savingsrules";
    private String URL_USER = "/user/" + Params.Const.USER_ID;

    private MockResponse goalsResponse;
    private MockResponse feedResponse;
    private MockResponse rulesResponse;
    private MockResponse userResponse;

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        String path = request.getPath();
        Log.d(App.TAG, "Request path: " + path);
        Log.d(App.TAG, "Request line: " + request.getRequestLine());
        if (path.equals(URL_GOALS)) {
            return getGoals();
        } else if (path.equals(URL_FEED)) {
            return getFeedResponse();
        } else if (path.equals(URL_RULES)) {
            return getRulesResponse();
        } else if (path.equals(URL_USER)) {
            return getUserResponse();
        } else {
            throw new IllegalArgumentException("Unsupported request: " + path);
        }
    }

    public void setGoalsResponse(GoalsWebResource.Resource goalsResource) {
        goalsResponse = new MockResponse()
                .setResponseCode(STATUS_OK)
                .setBody(new GoalsWebResource().getResponse(goalsResource));

    }

    public MockResponse getGoals() {
        if (goalsResponse == null) {
            return new MockResponse()
                    .setResponseCode(STATUS_OK)
                    .setBody(new GoalsWebResource().getResponse(GoalsWebResource.Resource.GOALS));
        }
        return goalsResponse;
    }

    public MockResponse getFeedResponse() {
        if (goalsResponse == null) {
            return new MockResponse()
                    .setResponseCode(STATUS_OK)
                    .setBody(new FeedWebResource().getResponse(FeedWebResource.Resource.FEED));
        }
        return goalsResponse;
    }

    public MockResponse getRulesResponse() {
        if (rulesResponse == null) {
            return new MockResponse()
                    .setResponseCode(STATUS_OK)
                    .setBody(new RulesWebResource().getResponse(RulesWebResource.Resource.RULES));
        }
        return rulesResponse;
    }

    public MockResponse getUserResponse() {
        if (userResponse == null) {
            return new MockResponse()
                    .setResponseCode(STATUS_OK)
                    .setBody(new UserWebResource().getResponse(UserWebResource.Resource.USER));
        }
        return userResponse;
    }
}
