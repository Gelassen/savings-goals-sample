package com.example.qapitalinterview;


import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.utils.TestObservable;
import com.example.qapitalinterview.view.IGoalView;
import com.example.qapitalinterview.view.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.observers.TestObserver;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GoalsPresenterTest extends BaseTest {

    @Inject
    IModel model;

    private IGoalView view;

    private SavingsGoalPresenter presenter;

    private TestObservable<SavingsGoals> testObservable;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        view = mock(IGoalView.class);
        presenter = new SavingsGoalPresenter(RuntimeEnvironment.application, view);

        testObservable = TestObservable.testObservable();
        when(model.getSavingGoals()).thenReturn(testObservable);
    }

    @Test
    public void testSavingsGoalPresenter() throws Exception {
        SavingsGoals goals = new SavingsGoals();
        testObservable.onNext(goals);
        assertFalse(presenter.getModel() == null);
        assertFalse(presenter.getView() == null);
    }

    @Test
    public void testShowData() throws Exception {
        SavingsGoals goals = mock(SavingsGoals.class);
        when(goals.getSavingsGoals()).thenReturn(Collections.singletonList(new SavingsGoal()));

        presenter.onRefreshData();
        testObservable.onNext(goals);

        verify(view).showData(any());
    }

    @Test
    public void testShowEmptyList() throws Exception {
        presenter.onRefreshData();
        testObservable.onNext(null);

        verify(view).showEmptyScreen(false);
    }

    @Test
    public void testShowDataWhenThereIsNoData() throws Exception {
        SavingsGoals goals = new SavingsGoals();

        presenter.onRefreshData();
        testObservable.onNext(goals);

        verify(view).showEmptyScreen(false);
    }

}
