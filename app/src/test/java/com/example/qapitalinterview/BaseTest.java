package com.example.qapitalinterview;


import android.util.Log;

import com.example.qapitalinterview.di.TestComponent;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public class BaseTest {

    /*package*/ TestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) TestApplication.getAppComponent();
    }

}
