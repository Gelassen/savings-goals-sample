package com.example.qapitalinterview.view.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.view.GoalDetailsActivity;

import java.util.List;

/**
 * Created by John on 10/30/2016.
 */

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private List<SavingsGoal> model;
    private Activity activity;
    private GoalDetailsInteractor interactor;

    public GoalsAdapter(Activity activity) {
        this.activity = activity;
        this.interactor = new GoalDetailsInteractor();
    }

    public void setModel(List<SavingsGoal> model) {
        this.model = model;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_goal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SavingsGoal goal = model.get(position);
        holder.title.setText(goal.getName());
        holder.price.setText(interactor.getTargetBalance(holder.itemView.getContext(), goal));
        Glide.with(holder.itemView.getContext())
                .load(goal.getGoalImageURL())
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoalDetailsActivity.start(v.getContext(), goal.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView price;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.goal_image);
            title = (TextView) view.findViewById(R.id.goal_name);
            price = (TextView) view.findViewById(R.id.goal_price);
        }
    }
}
