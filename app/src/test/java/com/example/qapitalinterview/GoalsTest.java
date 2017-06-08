package com.example.qapitalinterview;


import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.IGoalView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GoalsTest extends BaseTest {

    @Inject
    IModel model;

    private IGoalView view;

    private SavingsGoalPresenter presenter;
    private SavingsGoals stubResponse;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        view = mock(IGoalView.class);
        presenter = new SavingsGoalPresenter(RuntimeEnvironment.application, view);

        stubResponse = testUtils.getGson().fromJson(testUtils.readString("json/goals.json"), SavingsGoals.class);

        doAnswer(invocation -> Observable.just(stubResponse))
                .when(model)
                .getSavingGoals();
    }

    @Test
    public void testSavingsGoalPresenter() throws Exception {
        assertFalse(presenter.getModel() == null);
        assertFalse(presenter.getView() == null);
    }

    @Test
    public void testShowData() throws Exception {

        presenter.onRefreshData();

        verify(view).showData(stubResponse.getSavingsGoals());

    }

}
