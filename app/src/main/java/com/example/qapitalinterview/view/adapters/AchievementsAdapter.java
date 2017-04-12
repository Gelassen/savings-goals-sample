package com.example.qapitalinterview.view.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.storage.FilterPermStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * TRICK implement all extra classes as inner classes to better navigation and visibility
 * */
public class AchievementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_FILTER = 0;
    private static final int VIEW_TYPE_TOTAL = 1;
    private static final int VIEW_TYPE_CONTENT = 2;

    private static final int VIEW_POSITION_FILTER = 0;
    private static final int VIEW_POSITION_TOTAL = 1;
    private static final int VIEW_POSITION_CONTENT = 2;

    private GoalDetailsInteractor goalDetailsInteractor;
    private DataSource dataSource;
    private List<SavingsRule> savings = new ArrayList<>();
    private FilterAdapter.Listener filterListener;

    private String total;

    public AchievementsAdapter(FilterAdapter.Listener listener) {
        goalDetailsInteractor = new GoalDetailsInteractor();
        dataSource = new DataSource();
        dataSource.setOffset(2);
        filterListener = listener;
    }

    public void updateModel(List<Feed> model) {
        dataSource.updateModel(model);
        notifyDataSetChanged();
    }

    public void updateFilters(List<SavingsRule> rules, LinearLayoutManager layoutManager) {
        savings.clear();
        savings.addAll(rules);
        View view = layoutManager.findViewByPosition(VIEW_POSITION_FILTER);
        if (view != null) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_filter);
            recyclerView.getAdapter().notifyDataSetChanged();
            notifyItemChanged(VIEW_POSITION_FILTER);
        }
    }

    public void updateTotal(final String total, LinearLayoutManager layoutManager) {
        this.total = total;
        View view = layoutManager.findViewByPosition(VIEW_POSITION_TOTAL);
        if (view != null) {
            notifyItemChanged(VIEW_POSITION_TOTAL);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FILTER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_item_filters, parent, false);
                return new FilterViewHolder(view);
            case VIEW_TYPE_TOTAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_item_total, parent, false);
                return new TotalViewHolder(view);
            case VIEW_TYPE_CONTENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_item_achievments, parent, false);
                return new ViewHolder(view);
            default:
                throw new IllegalArgumentException("Unsupported view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_FILTER:
                FilterViewHolder viewHolderFilter = (FilterViewHolder) viewHolder;
                viewHolderFilter.updateModel(savings);
                Log.d(App.ISSUE, "Filter binding");
                break;
            case VIEW_TYPE_TOTAL:
                TotalViewHolder totalViewHolder = (TotalViewHolder) viewHolder;
                totalViewHolder.total.setText(total);
                break;
            case VIEW_TYPE_CONTENT:
                ViewHolder holder = (ViewHolder) viewHolder;
                Feed feed = dataSource.getFeed(position);
                holder.total.setText(goalDetailsInteractor.getBalance(feed.getAmount()));
                holder.achievement.setText(goalDetailsInteractor.getAchievement(feed.getMessage()));
                holder.elapsedTime.setText(goalDetailsInteractor.getElapsedTime(feed.getTime(), holder.itemView.getContext()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.getTotalViews();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case VIEW_POSITION_FILTER:
                return VIEW_TYPE_FILTER;
            case VIEW_POSITION_TOTAL:
                return VIEW_TYPE_TOTAL;
            default:
                return VIEW_TYPE_CONTENT;
        }
    }

    public void onDestroy(Context context) {
        // TRICK clean filter state. it is better to encapsulate on filter adapter
        // but the increased complexity would be too high and it is better to leave there
        FilterPermStorage.clean(context);
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

    private static class TotalViewHolder extends RecyclerView.ViewHolder {

        private TextView total;

        public TotalViewHolder(View itemView) {
            super(itemView);
            total = (TextView) itemView.findViewById(R.id.total_achievements);
        }
    }

    private class FilterViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;

        public FilterViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.list_filter);
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(
                            itemView.getContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false)
            );
            recyclerView.setAdapter(new FilterAdapter(itemView.getContext(), filterListener));
        }

        public void updateModel(List<SavingsRule> savings) {
            FilterAdapter adapter = (FilterAdapter) recyclerView.getAdapter();
            adapter.updateModel(savings);
        }
    }

    private class DataSource {
        private List<Feed> dataSource = new ArrayList<>();
        private int offset;

        public void updateModel(List<Feed> model) {
            dataSource.clear();
            dataSource.addAll(model);
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public Feed getFirstFeed() {
            return dataSource.get(0);
        }

        public Feed getFeed(int viewPosition) {
            return dataSource.get(getFeedPosition(viewPosition));
        }

        public int getFeedPosition(int viewPosition) {
            final int feedPosition = viewPosition - offset;
            if (feedPosition < 0) {
                throw new IllegalStateException("Did you updateModel custom views amount? " +
                        "View position of data should start after " + Math.abs(feedPosition) + " positions");
            }
            return feedPosition;
        }

        public int getTotalViews() {
            return dataSource.size() + offset;
        }

        public List<Feed> getData() {
            return dataSource;
        }
    }

}
