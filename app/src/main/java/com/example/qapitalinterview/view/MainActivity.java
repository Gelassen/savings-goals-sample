package com.example.qapitalinterview.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    private View placeholder;
    private View placeholderNoData;
    private View placeholderWaiting;

    @Inject
    protected IGoalsPresenter presenter;

    protected ViewComponent viewComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placeholder = findViewById(R.id.placeholder);
        placeholderWaiting = findViewById(R.id.placeholder_waiting);
        placeholderNoData = findViewById(R.id.placeholder_no_data);

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

        placeholder.setVisibility(View.INVISIBLE);
        placeholderNoData.setVisibility(View.GONE);
        placeholderWaiting.setVisibility(View.GONE);
    }

    public IGoalsPresenter getPresenter() {
        return presenter;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public GoalsAdapter getAdapter() {
        return adapter;
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
    public void showEmptyScreen(boolean isShow) {
        placeholder.setVisibility(isShow ? View.VISIBLE : View.GONE);
        placeholderNoData.setVisibility(View.VISIBLE);
        placeholderWaiting.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_goals, Toast.LENGTH_SHORT).show();
        placeholder.setVisibility(View.VISIBLE);
        placeholderNoData.setVisibility(View.VISIBLE);
        placeholderWaiting.setVisibility(View.GONE);
    }

    @Override
    public void showInProgress(boolean isInProgress) {
        placeholder.setVisibility(View.VISIBLE);
        placeholderNoData.setVisibility(isInProgress ? View.GONE : View.VISIBLE);
        placeholderWaiting.setVisibility(isInProgress ? View.VISIBLE : View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

}
