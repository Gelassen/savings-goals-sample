package com.example.qapitalinterview.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.di.DaggerViewComponent;
import com.example.qapitalinterview.di.ViewComponent;
import com.example.qapitalinterview.di.ViewModule;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.view.IGoalView;
import com.example.qapitalinterview.view.adapters.GoalsAdapter;

import java.util.List;

import javax.inject.Inject;

public class GoalFragment extends Fragment implements IGoalView {

    private RecyclerView recyclerView;
    private GoalsAdapter adapter;

    private View placeholder;
    private View placeholderNoData;
    private View placeholderWaiting;

    @Inject
    IGoalsPresenter presenter;

    private ViewComponent viewComponent;

    public static GoalFragment newInstance() {
        return new GoalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        placeholder = view.findViewById(R.id.placeholder);
        placeholderWaiting = view.findViewById(R.id.placeholder_waiting);
        placeholderNoData = view.findViewById(R.id.placeholder_no_data);
        initViewComponent();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        adapter = new GoalsAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        presenter.onRefreshData();
    }

    public void setViewComponent(ViewComponent viewComponent) {
        this.viewComponent = viewComponent;
    }

    private void initViewComponent() {
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewModule(new ViewModule(getActivity(), this))
                    .build();
        }
        viewComponent.inject(this);
    }

    @Override
    public void showData(final List<SavingsGoal> data) {
        getActivity().runOnUiThread(new Runnable() {
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
        Toast.makeText(getContext(), R.string.error_goals, Toast.LENGTH_SHORT).show();
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
        return getActivity();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }
}
