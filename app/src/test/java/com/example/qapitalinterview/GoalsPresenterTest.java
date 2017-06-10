package com.example.qapitalinterview;


import android.os.Bundle;

import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.IGoalView;
import com.example.qapitalinterview.view.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GoalsPresenterTest extends BaseTest {

    @Inject
    IModel model;

    private IGoalView view;

    private SavingsGoalPresenter presenter;
    private SavingsGoals stubResponse;

    private MainActivity activity;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        view = mock(IGoalView.class);
        presenter = new SavingsGoalPresenter(RuntimeEnvironment.application, view);

        stubResponse = testUtils.getGson().fromJson(testUtils.readString("json/goals.json"), SavingsGoals.class);

        activity = Robolectric.setupActivity(MainActivity.class);

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

    @Test
    public void testShowEmptyList() throws Exception {
        doAnswer(invocation -> Observable.just(null))
                .when(model)
                .getSavingGoals();

        presenter.onRefreshData();
        verify(view).showError();
    }

    @Test
    public void testShowDataWhenThereIsNoData() throws Exception {
        doAnswer(invocation -> Observable.just(new ArrayList<SavingsGoals>()))
                .when(model)
                .getSavingGoals();

        presenter.onRefreshData();
        verify(view).showError();
    }

}
