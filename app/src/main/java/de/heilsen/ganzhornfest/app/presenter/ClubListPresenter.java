package de.heilsen.ganzhornfest.app.presenter;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.interactor.ClubListInteractor;

public class ClubListPresenter extends Presenter<ClubListPresenter.View> {

    private ClubListInteractor clubListInteractor;

    public ClubListPresenter(ClubListInteractor clubListInteractor) {
        this.clubListInteractor = clubListInteractor;
    }

    public void showList() {
        getView().showLoading();
        clubListInteractor.listClubs(
                new ClubListInteractor.Callback() {
                    @Override
                    public void showClubList(List<Club> clubList) {
                        getView().showList(clubList);
                        getView().hideLoading();
                    }
                });
    }

    public void showItem(final Club club) {
            clubListInteractor.selectClub(club.getName(), new ClubListInteractor.DetailCallback() {
                public void showClubDetail(Club club) {
                    getView().openClubDetail(club.getName());
                }

                @Override
                public void emptyClubDetail() {
                }
            });

    }

    public interface View extends Presenter.View {
        void showList(List<Club> clubList);

        void openClubDetail(String clubName);
    }
}
