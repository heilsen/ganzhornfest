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

import com.google.android.gms.maps.MapView;

import java.util.List;

import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemConverter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.app.ui.map.MapDelegator;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemSection;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DetailFragment extends IsInBottomNavActivityFragment implements DetailPresenter.DetailView {

    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_TYPE = "item_type";
    private ListableItemType itemType;
    private String itemName;
    private RecyclerView recyclerView;
    private DetailPresenter detailPresenter;
//    private MapView mapView;

    public static DetailFragment newInstance(ListableItemType itemType, String name) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_NAME, name);
        args.putSerializable(ARG_ITEM_TYPE, itemType);
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
            itemType = (ListableItemType) getArguments().getSerializable(ARG_ITEM_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        injectViews(rootView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new MapDelegator());
        setupRecyclerview();
        return rootView;
    }

    private void injectViews(View view) {
        recyclerView = view.findViewById(R.id.item_recycler_view);
        detailPresenter = ((GanzhornfestApplication) getActivity().getApplication()).getDi().detailPresenter();
        detailPresenter.attachView(this);
        detailPresenter.show(itemType, itemName);
//        mapView = view.findViewById(R.id.map_view);
    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getTabbedActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
//        mapView.onStart();
    }

    @Override
    public void showClubDetail(Club club) {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new ListableItemSection("Essen", ListableItemConverter.INSTANCE.fromOfferList(club.getFoodList()), ListableItemType.FOOD, detailPresenter));
        sectionAdapter.addSection(new ListableItemSection("Trinken", ListableItemConverter.INSTANCE.fromOfferList(club.getDrinkList()), ListableItemType.DRINK, detailPresenter));
        sectionAdapter.addSection(new ListableItemSection("Angebote", ListableItemConverter.INSTANCE.fromOfferList(club.getActionableOfferList()), ListableItemType.ACTIONABLE_OFFER, detailPresenter));

        recyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public void showOfferDetail(Offer offer, List<Club> clubList) {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new ListableItemSection("Vereine", ListableItemConverter.INSTANCE.fromClubList(clubList), ListableItemType.CLUB, detailPresenter));

        recyclerView.setAdapter(sectionAdapter);

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }

}
