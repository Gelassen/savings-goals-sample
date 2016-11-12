package com.example.qapitalinterview.view.adapters;

import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.databinding.ComponentBindGoalBinding;
import com.example.qapitalinterview.interactor.GoalsInteractor;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.datasource.CursorDataSource;
import com.example.qapitalinterview.model.datasource.IDataSource;

/**
 * Created by John on 10/30/2016.
 */

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private GoalsInteractor goalsInteractor;
    private IDataSource<Cursor, SavingsGoal> dataSource;

    public GoalsAdapter() {
        dataSource = new CursorDataSource();
        goalsInteractor = new GoalsInteractor();
    }

    public void setModel(Cursor data) {
        dataSource.setModel(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComponentBindGoalBinding viewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.component_bind_goal, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavingsGoal savingsGoal = dataSource.getItemForPosition(position);
        holder.goalBinding.setGoal(savingsGoal);
    }

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.getAmount();
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ComponentBindGoalBinding goalBinding;

        public ViewHolder(ComponentBindGoalBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.goalBinding = viewDataBinding;
        }
    }
}
