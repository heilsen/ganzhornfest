package de.heilsen.ganzhornfest.app.presenter;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.interactor.ClubListInteractor;
import de.heilsen.ganzhornfest.domain.interactor.OfferListInteractor;

public class ListPresenter extends Presenter<ListPresenter.View> {

    private ClubListInteractor clubListInteractor;
    private OfferListInteractor offerListInteractor;
    private ListableItemType listableItemType;

    public ListPresenter(ClubListInteractor clubListInteractor, OfferListInteractor offerListInteractor) {
        this.clubListInteractor = clubListInteractor;
        this.offerListInteractor = offerListInteractor;
    }

    public <T extends Offer> void showListOfType(ListableItemType listableItemType) {
        this.listableItemType = listableItemType;
        getView().showLoading();
        final ListableItemConverter listableItemConverter = ListableItemConverter.INSTANCE;
        if (listableItemType == ListableItemType.CLUB) {
            clubListInteractor.listClubs(
                    new ClubListInteractor.Callback() {
                        @Override
                        public void showClubList(List<Club> clubList) {
                            getView().showList(listableItemConverter.fromClubList(clubList));
                            getView().hideLoading();
                        }
                    });
        }
        OfferListInteractor.Callback callback = new OfferListInteractor.Callback() {
            @Override
            public void showOfferList(List<Offer> offerList) {
                getView().showList(listableItemConverter.fromOfferList(offerList));
                getView().hideLoading();
            }
        };
        if (listableItemType == ListableItemType.FOOD) {
            offerListInteractor.listOffer(OfferType.FOOD, callback);
        }
        if (listableItemType == ListableItemType.DRINK) {
            offerListInteractor.listOffer(OfferType.DRINK, callback);
        }
        if (listableItemType == ListableItemType.ACTIONABLE_OFFER) {
            offerListInteractor.listOffer(OfferType.ACTIONABLE_OFFER, callback);
        }
    }

    public void showItem(final ListableItemType listableItemType, final ListableItem listableItem) {
        if (listableItemType == ListableItemType.CLUB) {
            clubListInteractor.selectClub(listableItem.getName(), new ClubListInteractor.DetailCallback() {
                public void showClubDetail(Club club) {
                    getView().openItemDetail(listableItemType, club.getName());
                }
            });
        }
        OfferListInteractor.DetailCallback detailCallback = new OfferListInteractor.DetailCallback() {
            @Override
            public void showOfferDetail(Offer offer) {
                getView().openItemDetail(listableItemType, offer.getName());
            }
        };
        if (listableItemType == ListableItemType.FOOD) {
            offerListInteractor.selectOffer(OfferType.FOOD, listableItem.getName(), detailCallback);
        }
        if (listableItemType == ListableItemType.DRINK) {
            offerListInteractor.selectOffer(OfferType.DRINK, listableItem.getName(), detailCallback);
        }
        if (listableItemType == ListableItemType.ACTIONABLE_OFFER) {
            offerListInteractor.selectOffer(OfferType.ACTIONABLE_OFFER, listableItem.getName(), detailCallback);
        }
    }

    public interface View extends Presenter.View {
        void showList(List<ListableItem> listableItems);

        void openItemDetail(ListableItemType itemType, String name);
    }
}
