package com.example.qapitalinterview.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.entity.FilterManager;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.presenter.GoalDetailsPresenter;
import com.example.qapitalinterview.presenter.IGoalDetailsPresenter;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;
import com.example.qapitalinterview.view.adapters.FilterAdapter;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


/**
 * Created by John on 10/30/2016.
 */

public class GoalDetailsActivity extends BaseActivity implements
        IGoalDetailsView,
        FilterAdapter.Listener {

    public static final String EXTRA_GOAL_ID = "EXTRA_GOAL_ID";
    public static final String EXTRA_FILTER_ID = "EXTRA_FILTER_ID";
    public static final String EXTRA_FILTER_APPLIED = "EXTRA_FILTER_APPLIED";

    public static void start(Context context, int goalId) {
        Intent intent = new Intent(context, GoalDetailsActivity.class);
        intent.putExtra(EXTRA_GOAL_ID, goalId);
        context.startActivity(intent);
    }

    private IGoalDetailsPresenter presenter;
    private GoalDetailsInteractor interactor;

    public static void startTransition(Activity context, View transitView, String transitName, int goalId) {
        boolean isLollipop = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        // FIXME have a visual artifact, fix in future and turn off animation for now
        boolean turnOff = false;
        if (turnOff) {
            Intent intent = new Intent(context, GoalDetailsActivity.class);
            intent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(EXTRA_GOAL_ID, goalId);
            ActivityOptions.makeSceneTransitionAnimation(context);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context, transitView, transitName);
            context.startActivity(intent, options.toBundle());
        } else {
            start(context, goalId);
        }

    }

    private ImageView goalImageView;
    private RecyclerView listAchievements;

    private TextView goalTitle;
    private TextView goalBalance;
    private ProgressBar goalProgress;

    private TextView goalTitleFinal;
    private TextView goalBalanceFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details_new);

//        GoalDetailsActivityNewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_details_new);
//        binding.setGoal(new BindedSavingsGoal());
        init(true);

        getSupportActionBar().setTitle("");
        interactor = new GoalDetailsInteractor();

        goalImageView = (ImageView) findViewById(R.id.goal_image);

        goalTitle = (TextView) findViewById(R.id.goal_progress_title);
        goalBalance = (TextView) findViewById(R.id.goal_progress_cash);
        goalProgress = (ProgressBar) findViewById(R.id.goal_progress);

        goalTitleFinal = (TextView) findViewById(R.id.goal_progress_final);
        goalBalanceFinal = (TextView) findViewById(R.id.goal_progress_cash_final);

        listAchievements = (RecyclerView) findViewById(R.id.list_achievements);
        listAchievements.setAdapter(new AchievementsAdapter(this));
        listAchievements.setLayoutManager(new LinearLayoutManager(this));

        presenter = new GoalDetailsPresenter(this, new Model(this), this);
        presenter.onUploadDetails(getIntent().getIntExtra(EXTRA_GOAL_ID, 0));

        Bundle args = new Bundle();
        args.putString(EXTRA_GOAL_ID, String.valueOf(getIntent().getIntExtra(EXTRA_GOAL_ID, 0)));
        presenter.onCheckCachedData(getSupportLoaderManager(), args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_goal_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(this, R.string.placeholder_edit_screen, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AchievementsAdapter adapter = (AchievementsAdapter) listAchievements.getAdapter();
        adapter.onDestroy(this);
    }

    @Override
    public void showGoal(SavingsGoal goal) {
        Glide.with(this)
                .load(goal.getGoalImageURL())
                .error(R.mipmap.ic_launcher)
                .into(goalImageView);

        goalTitle.setText(goal.getName());
        goalBalance.setText(interactor.getExtendedTargetBalance(this, goal));
        goalProgress.setProgress(interactor.getGoalProgress(goal));

        goalTitleFinal.setText(goal.getName());
        goalBalanceFinal.setText(interactor.getExtendedTargetBalance(this, goal));
    }

    @Override
    public void showFilters(List<SavingsRule> rules) {
        AchievementsAdapter adapter = (AchievementsAdapter) listAchievements.getAdapter();
        adapter.updateFilters(rules, (LinearLayoutManager) listAchievements.getLayoutManager());
    }

    @Override
    public void showAchievements(List<Feed> feeds) {
        AchievementsAdapter achievementsAdapter = (AchievementsAdapter) listAchievements.getAdapter();
        achievementsAdapter.updateModel(feeds);
    }

    @Override
    public void showWeeklyProgress(String formattedValue) {
        Log.d(App.TAG, "Total: " + formattedValue);
        AchievementsAdapter achievementsAdapter = (AchievementsAdapter) listAchievements.getAdapter();
        achievementsAdapter.updateTotal(formattedValue, (LinearLayoutManager) listAchievements.getLayoutManager());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.error_goals), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilterClick(SavingsRule filter) {
        Log.d(App.TAG, "onFilterClick: " + filter.getType());
        final String goalId = String.valueOf(getIntent().getIntExtra(EXTRA_GOAL_ID, 0));

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FILTER_ID, String.valueOf(filter.getId()));
        bundle.putString(EXTRA_GOAL_ID, goalId);
        bundle.putBoolean(EXTRA_FILTER_APPLIED, new FilterManager().isFilterApplied(filter));
        presenter.onApplyFilter(getSupportLoaderManager(), bundle);
    }
}
