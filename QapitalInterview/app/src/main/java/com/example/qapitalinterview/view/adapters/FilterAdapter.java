package com.example.qapitalinterview.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qapitalinterview.R;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private List<SavingsRule> dataSource = new ArrayList<>();

    public void updateModel(List<SavingsRule> model) {
        this.dataSource.clear();;
        this.dataSource.addAll(model);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete me
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*package*/ class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
