package com.example.qapitalinterview.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.Feed;

import java.util.ArrayList;
import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    private GoalDetailsInteractor goalDetailsInteractor;
    private List<Feed> dataSource = new ArrayList<>();

    public AchievementsAdapter() {
        goalDetailsInteractor = new GoalDetailsInteractor();
    }

    public void update(List<Feed> model) {
        dataSource.clear();
        dataSource.addAll(model);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_item_achievments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feed feed = dataSource.get(position);
        holder.total.setText(goalDetailsInteractor.getBalance(feed.getAmount()));
        holder.achievement.setText(goalDetailsInteractor.getAchievement(feed.getMessage()));
        holder.elapsedTime.setText(goalDetailsInteractor.getElapsedTime(feed.getTime(), holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView achievement;
        private TextView elapsedTime;
        private TextView total;

        public ViewHolder(View itemView) {
            super(itemView);
            achievement = (TextView) itemView.findViewById(R.id.achievement_title);
            elapsedTime = (TextView) itemView.findViewById(R.id.achievement_time);
            total = (TextView) itemView.findViewById(R.id.achievement_cost);
        }
    }
}
