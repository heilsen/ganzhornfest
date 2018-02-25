package de.heilsen.ganzhornfest;


import de.heilsen.ganzhornfest.interactor.GetClubList;

public class ServiceLocator {
    private static ServiceLocator soleInstance;
    private ClubListPresenter clubListPresenter;
    private GetClubList getClubList;

    public ServiceLocator(GetClubList getClubList) {
        this.getClubList = getClubList;
        this.clubListPresenter = new ClubListPresenter(getClubList);
    }

    public static void load(ServiceLocator serviceLocator) {
        soleInstance = serviceLocator;
    }

    public static GetClubList getClubList() {
        return soleInstance.getClubList;
    }

    public static ClubListPresenter clubListPresenter() {
        return soleInstance.clubListPresenter;
    }

}
