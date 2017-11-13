package com.example.qapitalinterview.webresources;


import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.di.ApiModule;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.rules.ExternalResource;

import java.io.IOException;

public class ServerRule extends ExternalResource {

    private MockWebServer webServer;
    private AppServerDispatcher dispatcher;
    private IApi api;

    public AppServerDispatcher getDispatcher() {
        return dispatcher;
    }

    public IApi getApi() {
        return api;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        dispatcher = new AppServerDispatcher();

        webServer = new MockWebServer();
        webServer.start();
        webServer.setDispatcher(dispatcher);
        api = ApiModule.getApiInterface(webServer.url("/").toString());
    }

    @Override
    protected void after() {
        super.after();
        try {
            webServer.shutdown();
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to turn down server", e);
        }
    }
}
