package de.heilsen.ganzhornfest.di;


import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.presenter.ClubDetailPresenter;
import de.heilsen.ganzhornfest.activity.ClubListAdapter;
import de.heilsen.ganzhornfest.presenter.ClubListPresenter;
import de.heilsen.ganzhornfest.interactor.GetClubList;
import de.heilsen.ganzhornfest.interactor.ShowClubDetail;
import de.heilsen.ganzhornfest.interactor.ThreadedShowClubDetail;

public class ServiceLocator implements ClubListServiceLocator, ClubDetailServiceLocator {
    private static ServiceLocator soleInstance;
    private ClubListPresenter clubListPresenter;
    private ClubListAdapter adapter;
    private ClubDetailPresenter clubDetailPresenter;

    public ServiceLocator(GetClubList getClubList, ShowClubDetail showClubDetail) {
        this.clubListPresenter = new ClubListPresenter(getClubList);
        this.adapter = new ClubListAdapter(clubListPresenter);
        this.clubDetailPresenter = new ClubDetailPresenter(showClubDetail);
    }

    public static void load(ServiceLocator serviceLocator) {
        soleInstance = serviceLocator;
    }

    public static ServiceLocator locator() {
        return soleInstance;
    }

    @Override
    public ClubListPresenter clubListPresenter() {
        return clubListPresenter;
    }

    @Override
    public ClubListAdapter clubListAdapter() {
        return adapter;
    }

    @Override
    public ClubDetailPresenter clubDetailPresenter() {
        return clubDetailPresenter;
    }

    @Override
    public void loadClub(Club club) {
        clubDetailPresenter = new ClubDetailPresenter(new ThreadedShowClubDetail(club));
    }
}
