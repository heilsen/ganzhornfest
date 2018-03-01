package de.heilsen.ganzhornfest;


import de.heilsen.ganzhornfest.interactor.GetClubList;

public class ServiceLocator {
    private static ServiceLocator soleInstance;
    private ClubListPresenter clubListPresenter;
    private ClubListAdapter adapter;

    public ServiceLocator(GetClubList getClubList) {
        this.clubListPresenter = new ClubListPresenter(getClubList);
        this.adapter = new ClubListAdapter(clubListPresenter);
    }

    public static void load(ServiceLocator serviceLocator) {
        soleInstance = serviceLocator;
    }

    public static ClubListPresenter clubListPresenter() {
        return soleInstance.clubListPresenter;
    }

    public static ClubListAdapter clubListAdapter() {
        return soleInstance.adapter;
    }

}
