package com.example.qapitalinterview.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import java.util.List;

public class MainActivity extends BaseActivity implements IGoalView, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private GoalsAdapter adapter;
    private IGoalsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new GoalsAdapter(this);
        recyclerView.setAdapter(adapter);

        presenter = new SavingsGoalPresenter(this);
        presenter.onRefreshData();

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void showData() {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void showData(final List<SavingsGoal> data) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                adapter.setModel(data);
//            }
//        });
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_goals, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.contentUri(Contract.GoalTable.class), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;
        Log.d(App.TAG, "Data: " + data.getCount());
        CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
        List<SavingsGoal> goals = converter.convert(data);
        adapter.setModel(goals);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
