package com.example.qapitalinterview.view.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.storage.FilterPermStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    public interface Listener {
        void onFilterClick(final SavingsRule savingsRule);
    }

    private GoalDetailsInteractor goalDetailsInteractor;

    private List<SavingsRule> dataSource = new ArrayList<>();
    private FilterPermStorage selectedFilter;
    private Listener listener;

    public FilterAdapter(Context context, Listener listener) {
        this.listener = listener;
        this.goalDetailsInteractor = new GoalDetailsInteractor();
        this.selectedFilter = new FilterPermStorage(context);
    }

    public void updateModel(List<SavingsRule> model) {
        this.dataSource.clear();
        this.dataSource.addAll(model);
        this.dataSource.add(0, goalDetailsInteractor.getAllItemsFilter());
        selectedFilter.initFilters(dataSource);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_item_rule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SavingsRule rule = dataSource.get(position);
        holder.itemView.setSelected(selectedFilter.isApplied(rule.getId()));
        holder.item.setText(String.valueOf(rule.getType().charAt(0)).toUpperCase());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFilter.changeState(rule.getId(), dataSource);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onFilterClick(rule);
                }
            }
        });
        Log.d(App.FILTER, "Selection " + holder.itemView.isSelected());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void emulateClick(final RecyclerView.ViewHolder clickedView) {
        ViewHolder vHolder = (ViewHolder) clickedView;
        vHolder.itemView.performClick();
    }

    /*package*/ class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.filter_item);
        }
    }
}
