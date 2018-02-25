package de.heilsen.ganzhornfest;


import java.util.List;

import de.heilsen.ganzhornfest.interactor.GetClubList;

public class ClubListPresenter extends Presenter<ClubListPresenter.View> {

    private GetClubList getClubList;

    public ClubListPresenter(GetClubList getClubList) {
        this.getClubList = getClubList;
    }

    public void initialize() {
        getClubList.getAll(new GetClubList.Callback() {
            @Override
            public void onClubListLoaded(List<Club> clubList) {
                View view = getView();
                view.showLoading();
                view.hideLoading();
                view.showClubs(clubList);
            }
        });
    }

    public void onClubClicked(Club club) {
        View view = getView();
        view.openClubDetail(club);
    }

    public interface View extends Presenter.View {
        void showClubs(List<Club> clubs);
        void openClubDetail(Club club);
    }
}
