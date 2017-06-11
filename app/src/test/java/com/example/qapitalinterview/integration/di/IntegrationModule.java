package com.example.qapitalinterview.integration.di;


import android.content.Context;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.di.ApiModule;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.utils.Params;
import com.example.qapitalinterview.utils.TestUtils;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IntegrationModule {

    private Context context;
    private TestUtils testUtils;

    public IntegrationModule(Context context) {
        this.testUtils = new TestUtils();
        this.context = context;
    }

    @Singleton
    @Provides
    public IModel getModel() {
        return new Model(context);
    }

    @Singleton
    @Provides
    /*package*/ IApi getApiInterface() {
        try {
            return getApiInterfaceWithMockServer();
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to obtain Api interface", e);
        }
        throw new RuntimeException("Failed to create Api interface");
    }

    /*package*/ IApi getApiInterfaceWithMockServer() throws IOException {
        MockWebServer webServer = new MockWebServer();
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
        return ApiModule.getApiInterface(url.toString());
    }
}
