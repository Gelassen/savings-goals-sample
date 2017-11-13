package com.example.qapitalinterview.integration;


import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.utils.TestObservable;
import com.example.qapitalinterview.view.IGoalView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoalsPresenterIntegrationTest extends IntegrationBaseTest {

    @Inject
    IModel model;

    private SavingsGoalPresenter presenter;
    private IGoalView view;

    private TestObservable<SavingsGoals> testObservable;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        when(model.getSavingGoals()).thenReturn(testObservable);
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
