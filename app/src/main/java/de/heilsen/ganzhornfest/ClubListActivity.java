package de.heilsen.ganzhornfest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class ClubListActivity extends AppCompatActivity implements ClubListPresenter.View {
    private static final String LAYOUT_MANAGER_SAVED_STATE = "LayoutManagerSavedState";
    private ClubListPresenter presenter;
    private ClubListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        initDependencyInjection();

        initRecyclerView();
        initPresenter();

        restoreRecyclerViewState(savedInstanceState);
    }

    private void initDependencyInjection() {
        presenter = ServiceLocator.clubListPresenter();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = ServiceLocator.clubListAdapter();

        initViews();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation()));
    }

    private void initPresenter() {
        presenter.initialize();
        presenter.setView(this);
    }

    private void restoreRecyclerViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Parcelable layoutManagerSavedState = savedInstanceState.getParcelable(LAYOUT_MANAGER_SAVED_STATE);
            layoutManager.onRestoreInstanceState(layoutManagerSavedState);
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.club_list_recycler_view);
        progressBar = findViewById(R.id.club_list_progress_bar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveRecyclerViewState(outState);
    }

    private void saveRecyclerViewState(Bundle outState) {
        outState.putParcelable(LAYOUT_MANAGER_SAVED_STATE, layoutManager.onSaveInstanceState());
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showClubs(List<Club> clubs) {
        adapter.addAll(clubs);
    }

    @Override
    public void openClubDetail(Club club) {
        Intent intent = new Intent(this, ClubDetailActivity.class);
        startActivity(intent);
    }
}
