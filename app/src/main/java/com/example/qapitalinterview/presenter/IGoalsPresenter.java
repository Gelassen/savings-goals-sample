package com.example.qapitalinterview.presenter;

/**
 * Created by John on 10/30/2016.
 */

public interface IGoalsPresenter {
    void onItemClick(final int goalId);
    void onStop();
    void onRefreshData();
}
