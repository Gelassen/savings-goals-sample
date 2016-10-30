package com.example.qapitalinterview.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.presenter.GoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import java.util.List;

public class MainActivity extends BaseActivity implements IGoalView {

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
    }

    @Override
    public void showData(final List<SavingsGoal> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setModel(data);
            }
        });

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
}
