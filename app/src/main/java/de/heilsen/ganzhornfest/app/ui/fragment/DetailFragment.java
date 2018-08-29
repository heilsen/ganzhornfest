package de.heilsen.ganzhornfest.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.R;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemConverter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemSection;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.GeoLocation;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import static java.util.Collections.singletonList;

public class DetailFragment extends IsInBottomNavActivityFragment implements DetailPresenter.DetailView,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_TYPE = "item_type";
    private static final String TAG = "DetailFragment";
    private ListableItemType itemType;
    private String itemName;
    private RecyclerView recyclerView;
    private DetailPresenter detailPresenter;
    private MapView mapView;
    private List<Club> clubList;

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
        mapView.onCreate(savedInstanceState);
        setupRecyclerview();
        return rootView;
    }

    private void injectViews(View view) {
        recyclerView = view.findViewById(R.id.item_recycler_view);
        detailPresenter = ((GanzhornfestApplication) getActivity().getApplication()).getDi().detailPresenter();
        detailPresenter.attachView(this);
        detailPresenter.show(itemType, itemName);
        mapView = view.findViewById(R.id.map_view);
    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getTabbedActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void showClubDetail(Club club) {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        this.clubList = singletonList(club);

        if (!club.getFoodList().isEmpty())
            sectionAdapter.addSection(new ListableItemSection("Essen", ListableItemConverter.INSTANCE.fromOfferList(club.getFoodList()), ListableItemType.FOOD, detailPresenter));
        if (!club.getDrinkList().isEmpty())
            sectionAdapter.addSection(new ListableItemSection("Trinken", ListableItemConverter.INSTANCE.fromOfferList(club.getDrinkList()), ListableItemType.DRINK, detailPresenter));
        if (!club.getActionableOfferList().isEmpty())
            sectionAdapter.addSection(new ListableItemSection("Angebote", ListableItemConverter.INSTANCE.fromOfferList(club.getActionableOfferList()), ListableItemType.ACTIONABLE_OFFER, detailPresenter));

        mapView.getMapAsync(this);
        recyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public void showOfferDetail(Offer offer, List<Club> clubList) {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        sectionAdapter.addSection(new ListableItemSection("Vereine", ListableItemConverter.INSTANCE.fromClubList(clubList), ListableItemType.CLUB, detailPresenter));
        this.clubList = clubList;

        mapView.getMapAsync(this);
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
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapRestrictions(googleMap, 17, true, buildLatLngBounds(clubList));
        List<Marker> markers = addMarkers(googleMap, clubList);
        showInfoWindow(markers);
        googleMap.setOnInfoWindowClickListener(this);

    }

    private void showInfoWindow(List<Marker> markers) {
        if (markers.size() == 1) {
            markers.get(0).showInfoWindow();
        }
    }

    private void mapRestrictions(GoogleMap googleMap, int minZoom, boolean showZoomButtons,
                                 LatLngBounds latLngBounds) {
        googleMap.setMinZoomPreference(minZoom);
        googleMap.getUiSettings().setZoomControlsEnabled(showZoomButtons);
        googleMap.setLatLngBoundsForCameraTarget(latLngBounds);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), 10));
    }

    private List<Marker> addMarkers(GoogleMap googleMap, List<Club> clubList) {
        List<Marker> markers = new ArrayList<>();
        for (Club club : clubList) {
            for (GeoLocation geoLocation : club.getGeoLocations()) {
                LatLng latLng = new LatLng(geoLocation.getLat(), geoLocation.getLng());
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(club.getName()));
                marker.setTag(club.getName());
                markers.add(marker);
            }
        }
        return markers;
    }

    private LatLngBounds buildLatLngBounds(List<Club> clubList) {
        LatLngBounds.Builder latLngBoundsBuilder = LatLngBounds.builder();
        for (Club club : clubList) {
            for (GeoLocation geoLocation : club.getGeoLocations()) {
                LatLng latLng = new LatLng(geoLocation.getLat(), geoLocation.getLng());
                latLngBoundsBuilder.include(latLng);
            }
        }
        return latLngBoundsBuilder.build();
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        String clubName = (String) marker.getTag();
        FragmentTransaction
                fragmentTransaction = getTabbedActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.replace(R.id.tabbed_content, DetailFragment.newInstance(ListableItemType.CLUB, clubName));
        fragmentTransaction.commit();
    }
}
