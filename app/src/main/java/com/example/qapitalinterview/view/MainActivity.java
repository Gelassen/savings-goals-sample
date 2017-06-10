package com.example.qapitalinterview.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.di.DaggerViewComponent;
import com.example.qapitalinterview.di.ViewComponent;
import com.example.qapitalinterview.di.ViewModule;
import com.example.qapitalinterview.entity.Utils;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IGoalView {

    private RecyclerView recyclerView;
    private GoalsAdapter adapter;

    @Inject
    protected IGoalsPresenter presenter;

    protected ViewComponent viewComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewComponent();

        init(false);

        Utils.initStatusBarHeight(getWindow());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new GoalsAdapter(this);
        recyclerView.setAdapter(adapter);

        presenter.onRefreshData();
    }

    protected void initViewComponent() {
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewModule(new ViewModule(this, this))
                    .build();
        }
        viewComponent.inject(this);
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
