package de.heilsen.ganzhornfest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.activity.helper.SupportActionBarAdapter;
import de.heilsen.ganzhornfest.di.ServiceLocator;
import de.heilsen.ganzhornfest.presenter.ClubDetailPresenter;

public class ClubDetailActivity extends AppCompatActivity implements ClubDetailPresenter.View {
    private ClubDetailPresenter presenter;
    private TextView textView;
    private SupportActionBarAdapter supportActionBarAdapter;

    public static void open(Context context, Club club) {
        Intent intent = new Intent(context, ClubDetailActivity.class);
        intent.putExtra("clubName", club.getName());
        ServiceLocator.locator().loadClub(club);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        injectDependencies();

        initPresenter();

        initToolbar();
    }

    private void injectDependencies() {
        ServiceLocator serviceLocator = ServiceLocator.locator();

        presenter = serviceLocator.clubDetailPresenter();

        injectViews();
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.show();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportActionBarAdapter = new SupportActionBarAdapter(getSupportActionBar());
        supportActionBarAdapter.set(new SupportActionBarAdapter.Callback() {
            @Override
            public void onActionBar(ActionBar actionBar) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        });
    }

    private void setTitle(final String title) {
        supportActionBarAdapter.set(new SupportActionBarAdapter.Callback() {
            @Override
            public void onActionBar(ActionBar actionBar) {
                actionBar.setTitle(title);
            }
        });
    }

    private void injectViews() {
        textView = findViewById(R.id.activity_club_detail_text_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showLoading() {
        //TODO
    }

    @Override
    public void hideLoading() {
        //TODO
    }

    @Override
    public void showDetail(Club club) {
        setTitle(club.getName());
        textView.setText(club.getDescription());
    }

    @Override
    public void showEmpty() {
        textView.setText("Club not found");
    }

}
