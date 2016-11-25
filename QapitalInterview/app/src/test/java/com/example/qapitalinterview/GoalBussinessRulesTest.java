package com.example.qapitalinterview;


import com.example.qapitalinterview.interactor.GoalDetailsInteractor;

import org.junit.Before;
import org.junit.Test;

public class GoalBussinessRulesTest {

    private GoalDetailsInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new GoalDetailsInteractor();
    }

    @Test
    public void elapsedTimeIsValid() throws Exception {
//        interactor.getElapsedTime()
    }
}
