package de.heilsen.ganzhornfest.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.di.ServiceLocator;
import de.heilsen.ganzhornfest.presenter.ClubListPresenter;

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
        injectDependencies();

        initViews();

        initPresenter();

        restoreRecyclerViewState(savedInstanceState);
    }

    private void injectDependencies() {
        ServiceLocator serviceLocator = ServiceLocator.locator();

        presenter = serviceLocator.clubListPresenter();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = serviceLocator.clubListAdapter();

        injectViews();
    }

    private void initViews() {
        initToolbar();
        initRecyclerView();
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.show();
    }

    private void restoreRecyclerViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Parcelable layoutManagerSavedState = savedInstanceState.getParcelable(LAYOUT_MANAGER_SAVED_STATE);
            layoutManager.onRestoreInstanceState(layoutManagerSavedState);
            adapter.notifyDataSetChanged();
        }
    }

    private void injectViews() {
        recyclerView = findViewById(R.id.club_list_recycler_view);
        progressBar = findViewById(R.id.club_list_progress_bar);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation()));
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
        ClubDetailActivity.open(this, club);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setView(null);
        adapter = null;
    }
}
