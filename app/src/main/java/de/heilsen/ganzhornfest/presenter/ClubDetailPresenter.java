package de.heilsen.ganzhornfest.presenter;


import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.interactor.ShowClubDetail;

public class ClubDetailPresenter extends Presenter<ClubDetailPresenter.View> {
    private ShowClubDetail showClubDetail;

    public ClubDetailPresenter(ShowClubDetail showClubDetail) {
        this.showClubDetail = showClubDetail;
    }

    public void show() {
        getView();
        showClubDetail.execute(new ShowClubDetail.Callback() {
            @Override
            public void show(Club club) {
                if (club != null) {
                    getView().showDetail(club);
                } else {
                    getView().showEmpty();
                }
            }

        });
    }

    public interface View extends Presenter.View {
        void showDetail(Club club);
        void showEmpty();
    }
}
