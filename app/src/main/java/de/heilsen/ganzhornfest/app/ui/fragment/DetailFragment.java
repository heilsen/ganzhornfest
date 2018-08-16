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

import de.heilsen.ganzhornfest.app.GanzhornfestApplication;
import de.heilsen.ganzhornfest.app.R;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemType;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;

//TODO: add presenter
public class DetailFragment extends InsideTabbedActivityFragment implements DetailPresenter.DetailView {

    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_TYPE = "item_type";
    private ListableItemType itemType;
    private String itemName;
    private RecyclerView recyclerView;
    private DetailPresenter detailPresenter;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        injectViews(rootView);
        setupRecyclerview();
        return rootView;
    }



//    @Override
//    public void showDetail(Club club) {
//        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
//
//        sectionAdapter.addSection(new ListableItemSection("Essen", club.getFoodList()));
//        sectionAdapter.addSection(new ListableItemSection("Trinken", club.getDrinkList()));
//        sectionAdapter.addSection(new ListableItemSection("Angebote", club.getActionableOfferList()));
//
//        recyclerView.setAdapter(sectionAdapter);
//
//    }

    @Override
    public void showClubDetail(Club club) {

    }

    @Override
    public void showOfferDetail(Offer offer, List<Club> clubList) {

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
        detailPresenter = ((GanzhornfestApplication) getActivity().getApplication()).getDi().detailPresenter();
        detailPresenter.attachView(this);
        detailPresenter.show(itemType, itemName);
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
