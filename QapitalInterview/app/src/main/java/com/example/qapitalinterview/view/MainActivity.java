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
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.presenter.GoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import java.util.List;

public class MainActivity extends BaseActivity implements IGoalView, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private GoalsAdapter adapter;
    private GoalsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new GoalsAdapter();
        recyclerView.setAdapter(adapter);

        presenter = new SavingsGoalPresenter(this);
        presenter.onRefreshData();
//
//        GoalRepo goalRepo = new GoalRepo(this);
//        goalRepo.open();
//        Cursor cursor = goalRepo.getAllGoals();
//        int count = cursor.getCount();
//        goalRepo.close();


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
        adapter.setModel(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
