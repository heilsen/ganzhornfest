package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import de.heilsen.ganzhornfest.app.R;
import de.heilsen.ganzhornfest.app.di.ServiceLocator;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemSection;
import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

//TODO: add presenter
public class DetailFragment extends InsideTabbedActivityFragment implements DetailPresenter.View {

    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_DESCRIPTION = "item_description";
    private ListableItemType itemType;
    private String itemName;
    private RecyclerView recyclerView;
    private DetailPresenter detailPresenter;

    public static DetailFragment newInstance(ListableItemType itemType, String name) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NAME, name);
        args.putSerializable(ARG_ITEM_DESCRIPTION, itemType);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            itemName = getArguments().getString(ARG_ITEM_NAME);
            itemType = (ListableItemType) getArguments().getSerializable(ARG_ITEM_DESCRIPTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        injectViews(rootView);
        setupRecyclerview();
        return rootView;
    }

    @Override
    public void showDetail(Map<ListableItemType, List<? extends ListableItem>> infoMap) {
        //TODO: make infoMap a class ItemDetail or add the functionality to ListableItem
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        for (ListableItemType listableItemType : infoMap.keySet()) {
            if (hasItemsOfType(infoMap, listableItemType)) {
                sectionAdapter.addSection(new ListableItemSection(listableItemType.getItemName(), infoMap.get(listableItemType)));
            }
        }

        recyclerView.setAdapter(sectionAdapter);

    }

    private boolean hasItemsOfType(Map<ListableItemType, List<? extends ListableItem>> infoMap, ListableItemType listableItemType) {
        return !infoMap.get(listableItemType).isEmpty();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getTabbedActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void injectViews(View view) {
        recyclerView = view.findViewById(R.id.item_recycler_view);
        detailPresenter = ServiceLocator.locator().detailPresenter();
        detailPresenter.attachView(this);
        detailPresenter.show(itemName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getTabbedActivity().onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected String getToolbarTitle() {
        return itemName;
    }

    @Override
    protected boolean showToolbarNavigationUp() {
        return true;
    }
}
