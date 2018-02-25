package de.heilsen.ganzhornfest;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import de.heilsen.ganzhornfest.interactor.ThreadedGetClubList;

public class ClubListActivity extends AppCompatActivity implements ClubListPresenter.View {
    ClubListPresenter presenter;
    private ClubListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        initDependencyInjection();
        initPresenter();
        initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.club_list_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClubListAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation()));

    }

    private void initPresenter() {
        presenter = ServiceLocator.clubListPresenter();
        presenter.initialize();
        presenter.setView(this);
    }

    private void initDependencyInjection() {
        ServiceLocator.load(new ServiceLocator(new ThreadedGetClubList(new FakeClubRepository())));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showClubs(List<Club> clubs) {
        adapter.addAll(clubs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openClubDetail(Club club) {
        Intent intent = new Intent(this, ClubDetailActivity.class);
        startActivity(intent);
    }
}
