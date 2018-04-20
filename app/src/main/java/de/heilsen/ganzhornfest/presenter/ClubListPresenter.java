package de.heilsen.ganzhornfest.presenter;

import java.util.List;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.interactor.GetClubList;

public class ClubListPresenter extends Presenter<ClubListPresenter.View> {

    private GetClubList getClubList;

    public ClubListPresenter(GetClubList getClubList) {
        this.getClubList = getClubList;
    }

    public void show() {
        getView().showLoading();
        getClubList.execute(
                new GetClubList.Callback() {
                    @Override
                    public void onClubListLoaded(List<Club> clubList) {
                        getView().showClubs(clubList);
                        getView().hideLoading();
                    }
                });
    }

    public void select(Club club) {
        getView().openClubDetail(club);
    }

    public interface View extends Presenter.View {
        void showClubs(List<Club> clubs);

        void openClubDetail(Club club);
    }
}
