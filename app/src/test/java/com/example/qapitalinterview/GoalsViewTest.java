package com.example.qapitalinterview;



import com.example.qapitalinterview.di.TestViewComponent;
import com.example.qapitalinterview.fake.TestMainActivity;
import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;


import java.util.ArrayList;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

public class GoalsViewTest extends BaseTest {

    @Inject
    IGoalsPresenter presenter;

    private TestMainActivity activity;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = Robolectric.setupActivity(TestMainActivity.class);
        TestViewComponent testViewComponent = (TestViewComponent) activity.getViewComponent();
        testViewComponent.inject(this);
    }

    // TODO delete after completed the rest activity methods
    @Test
    public void testActivity() throws Exception {
        activity.showData(new ArrayList<>());
        verify(presenter).onRefreshData();
    }

    @Test
    public void testOnResume() throws Exception {
        activity.onResume();
        verify(presenter).onRefreshData();
    }

    @Test
    public void testOnStop() throws Exception {
        activity.onStop();
        verify(presenter).onStop();
    }

}
