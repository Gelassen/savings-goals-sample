package com.example.qapitalinterview;


import com.example.qapitalinterview.fake.TestMainActivity;
import com.example.qapitalinterview.fake.TestSampleActivity;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.util.ArrayList;

public class GoalsViewTest extends BaseTest {

    private TestMainActivity activity;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = Robolectric.setupActivity(TestMainActivity.class);
    }

    @Test
    public void testActivity() throws Exception {
        activity.showData(new ArrayList<>());
    }

}
