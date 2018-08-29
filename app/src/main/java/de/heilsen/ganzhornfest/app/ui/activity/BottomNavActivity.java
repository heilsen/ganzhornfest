package de.heilsen.ganzhornfest.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Objects;

import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.ui.fragment.DatePagerFragment;
import de.heilsen.ganzhornfest.app.ui.fragment.ListFragment;
import de.heilsen.ganzhornfest.app.ui.fragment.InfoFragment;
import de.heilsen.ganzhornfest.app.ui.fragment.MapFragment;

public class BottomNavActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        setupViews(savedInstanceState);
    }

    private void setContentView() {
        setContentView(R.layout.activity_bottom_navigation);
    }

    private void initViews() {
        setupToolbar();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayShowTitleEnabled(true);
        setToolbarTitle("Ganzhornfest");
    }

    private void setToolbarTitle(String toolbarTitle) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(toolbarTitle);
    }

    public void updateToolbar(String toolbarTitle, boolean showNavigationArrow) {
        setToolbarTitle(toolbarTitle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(showNavigationArrow);
    }

    private void setupViews(Bundle savedInstanceState) {
        setupToolbar();
        setupBottomNavigationView(savedInstanceState);
    }

    private void setupBottomNavigationView(Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().
                    add(R.id.tabbed_content, new InfoFragment()).
                    commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_info:
                        fragmentTransaction.replace(R.id.tabbed_content, new InfoFragment(), InfoFragment.TAG);
                        item.setChecked(true);
                        break;
                    case R.id.action_list:
                        fragmentTransaction.replace(R.id.tabbed_content, new ListFragment(), ListFragment.TAG);
                        item.setChecked(true);
                        break;
                    case R.id.action_map:
                        fragmentTransaction.replace(R.id.tabbed_content, new MapFragment(), MapFragment.TAG);
                        item.setChecked(true);
                        break;
                    case R.id.action_program:
                        fragmentTransaction.replace(R.id.tabbed_content,
                                DatePagerFragment.newInstance(
                                        "Programm",
                                        "program"),
                                DatePagerFragment.TAG);
                        item.setChecked(true);
                        break;
                    case R.id.action_bus:
                        fragmentTransaction.replace(R.id.tabbed_content,
                                DatePagerFragment.newInstance(
                                        "Busfahrplan",
                                        "bus",
                                        "Rückfahrzeiten (ab 19:30 Uhr) ab Haltestelle ZOB/Ballei"),
                                DatePagerFragment.TAG);
                        item.setChecked(true);
                        break;
                    default:
                        return false;
                }
                fragmentTransaction.commit();

                return true;
            }
        });
    }

}
