package de.heilsen.ganzhornfest.app.presenter;


import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.interactor.ClubInfoInteractor;
import de.heilsen.ganzhornfest.domain.interactor.OfferInfoInteractor;

public class DetailPresenter extends Presenter<DetailPresenter.DetailView> {
    private ClubInfoInteractor clubInfoInteractor;
    private OfferInfoInteractor offerInfoInteractor;

    public DetailPresenter(ClubInfoInteractor clubInfoInteractor, OfferInfoInteractor offerInfoInteractor) {
        this.clubInfoInteractor = clubInfoInteractor;
        this.offerInfoInteractor = offerInfoInteractor;
    }

    public void show(ListableItemType type, String name) {
        getView().showLoading();
        if (type == ListableItemType.CLUB) {
            clubInfoInteractor.show(name, new ClubInfoInteractor.Callback() {
                @Override
                public void show(Club club) {
                    if (club != null) {
                        getView().showClubDetail(club);
                    } else {
                        getView().showEmpty();
                    }
                }

                @Override
                public void showEmpty() {
                    getView().showEmpty();
                }

            });
        } else {
            offerInfoInteractor.showInfo(OfferType.FOOD, name, new OfferInfoInteractor.Callback() {
                @Override
                public void show(Offer offer, List<Club> clubList) {
                    getView().showOfferDetail(offer, clubList);
                }

                @Override
                public void showEmpty() {
                    getView().showEmpty();
                }
            });
        }
        getView().hideLoading();
    }

    public interface DetailView extends Presenter.View {
        void showClubDetail(Club club);
        void showOfferDetail(Offer offer, List<Club> clubList);
        void showEmpty();
    }
}
