package de.heilsen.ganzhornfest.app.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.app.R;
import de.heilsen.ganzhornfest.app.di.ApplicationComponent;
import de.heilsen.ganzhornfest.app.presenter.ListableItem;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;

public class ListFragment extends InsideTabbedActivityFragment implements ListPresenter.View {

    public static final String TAG = "ListFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListableItemAdapter adapter;
    private Spinner spinner;
    private ListPresenter listPresenter;

    public ListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.club_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectDependencies();
        injectViews();
        setupRecyclerView();
        setupSpinner();
    }

    private void injectDependencies() {
        layoutManager = new LinearLayoutManager(getTabbedActivity().getApplicationContext());
        ApplicationComponent di = ((GanzhornfestApplication) getActivity().getApplication()).getDi();
        listPresenter = di.listableItemPresenter();
        listPresenter.attachView(this);
        adapter = di.clubListAdapter();
    }

    private void injectViews() {
        recyclerView = getTabbedActivity().findViewById(R.id.club_list_recycler_view);
        spinner = getTabbedActivity().findViewById(R.id.category_spinner);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getTabbedActivity(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new CategorySpinnerOnItemSelectedListener(listPresenter));
        spinner.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showList(List<ListableItem> listableItems) {
        adapter.set(listableItems);
    }

    @Override
    public void openItemDetail(ListableItemType itemType, String name) {
        FragmentTransaction fragmentTransaction = getTabbedActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.replace(R.id.tabbed_content, DetailFragment.newInstance(itemType, name));
        fragmentTransaction.commit();
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.title_fragment_club_list);
    }

    @Override
    protected boolean showToolbarNavigationUp() {
        return false;
    }
}