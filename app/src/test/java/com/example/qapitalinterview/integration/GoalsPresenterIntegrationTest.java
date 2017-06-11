package com.example.qapitalinterview.integration;


import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.IGoalView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class GoalsPresenterIntegrationTest extends IntegrationBaseTest {

    private SavingsGoalPresenter presenter;
    private IGoalView view;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        view = mock(IGoalView.class);
        presenter = new SavingsGoalPresenter(RuntimeEnvironment.application, view);
    }

    @Test
    public void testInjectionIsValid() throws Exception {
        assertNotNull(presenter.getModel());
        assertNotNull(presenter.getView());
    }

    @Test
    public void testShowData() throws Exception {
        presenter.onRefreshData();
        verify(view).showData(new ArrayList<>());
    }
}
