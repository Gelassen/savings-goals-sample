package com.example.qapitalinterview.robots;


import android.content.ComponentName;
import android.content.Context;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.view.GoalDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GoalsRobot {

    public GoalsRobot seesGoalsList() {
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
        return this;
    }

    public GoalsRobot clickSecondItem() {
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(1, click()));
        return this;
    }

    public GoalsRobot seesGoalDetailsActivity(Context context) {
        intended(hasComponent(new ComponentName(context, GoalDetailsActivity.class)));
        return this;
    }
}
