package com.example.qapitalinterview;


import com.example.qapitalinterview.di.TestComponent;
import com.example.qapitalinterview.utils.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

    /*package*/ TestComponent component;
    /*package*/ TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) TestApplication.getAppComponent();
        testUtils = new TestUtils();
    }

}
