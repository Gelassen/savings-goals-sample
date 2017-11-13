package com.example.qapitalinterview.integration;

import com.example.qapitalinterview.BuildConfig;
import com.example.qapitalinterview.integration.di.DaggerIntegrationComponent;
import com.example.qapitalinterview.integration.di.IntegrationComponent;
import com.example.qapitalinterview.integration.di.IntegrationModule;
import com.example.qapitalinterview.integration.di.IntegrationTestApplication;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(
        application = IntegrationTestApplication.class,
        constants = BuildConfig.class,
        sdk = 21
)
public class IntegrationBaseTest {
    protected IntegrationComponent component;

    @Before
    public void setUp() throws Exception {
        component = (IntegrationComponent) IntegrationTestApplication.getAppComponent();
        component = DaggerIntegrationComponent.builder()
                .integrationModule(new IntegrationModule(RuntimeEnvironment.application))
                .build();
    }
}
