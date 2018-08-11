package de.heilsen.ganzhornfest.app.di;


import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListableItemsPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.interactor.GetClubInfoInteractor;
import de.heilsen.ganzhornfest.domain.interactor.GetListInteractor;

//TODO: replace with Dagger2
public class ServiceLocator implements ListableItemsServiceLocator, DetailServiceLocator {
    private static ServiceLocator soleInstance;
    private ListableItemsPresenter listableItemsPresenter;
    private GetClubInfoInteractor getClubInfoInteractor;
    private ListableItemAdapter adapter;
    private DetailPresenter detailPresenter;

    public ServiceLocator(GetListInteractor getListInteractor, GetClubInfoInteractor getClubInfoInteractor) {
        this.listableItemsPresenter = new ListableItemsPresenter(getListInteractor);
        this.getClubInfoInteractor = getClubInfoInteractor;
        this.adapter = new ListableItemAdapter(listableItemsPresenter);
        this.getClubInfoInteractor = getClubInfoInteractor;
        this.detailPresenter = new DetailPresenter(getClubInfoInteractor);
    }

    public static void load(ServiceLocator serviceLocator) {
        soleInstance = serviceLocator;
    }

    public static ServiceLocator locator() {
        return soleInstance;
    }

    @Override
    public ListableItemsPresenter clubListPresenter() {
        return listableItemsPresenter;
    }

    @Override
    public ListableItemAdapter clubListAdapter() {
        return adapter;
    }

    @Override
    public DetailPresenter detailPresenter() {
        return detailPresenter;
    }

    @Override
    public GetClubInfoInteractor getClubInfoInteractor() {
        return getClubInfoInteractor;
    }
}
