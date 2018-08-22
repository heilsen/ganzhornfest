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
                            getView().showList(ListableItemType.CLUB, listableItemConverter.fromClubList(clubList));
                            getView().hideLoading();
                        }
                    });
        }
        if (listableItemType == ListableItemType.FOOD) {
            offerListInteractor.listOffer(OfferType.FOOD, new OfferListInteractor.Callback() {
                @Override
                public void showOfferList(List<Offer> offerList) {
                    getView().showList(ListableItemType.FOOD, listableItemConverter.fromOfferList(offerList));
                    getView().hideLoading();
                }
            });
        }
        if (listableItemType == ListableItemType.DRINK) {
            offerListInteractor.listOffer(OfferType.DRINK, new OfferListInteractor.Callback() {
                @Override
                public void showOfferList(List<Offer> offerList) {
                    getView().showList(ListableItemType.DRINK, listableItemConverter.fromOfferList(offerList));
                    getView().hideLoading();
                }
            });
        }
        if (listableItemType == ListableItemType.ACTIONABLE_OFFER) {
            offerListInteractor.listOffer(OfferType.ACTIONABLE_OFFER, new OfferListInteractor.Callback() {
                @Override
                public void showOfferList(List<Offer> offerList) {
                    getView().showList(ListableItemType.ACTIONABLE_OFFER, listableItemConverter.fromOfferList(offerList));
                    getView().hideLoading();
                }
            });
        }
    }

    public void showItem(final ListableItemType listableItemType, final ListableItem listableItem) {
        if (listableItemType == ListableItemType.CLUB) {
            clubListInteractor.selectClub(listableItem.getName(), new ClubListInteractor.DetailCallback() {
                public void showClubDetail(Club club) {
                    getView().openItemDetail(listableItemType, club.getName());
                }

                @Override
                public void emptyClubDetail() {
                }
            });
        }
        OfferListInteractor.DetailCallback detailCallback = new OfferListInteractor.DetailCallback() {
            @Override
            public void showOfferDetail(Offer offer) {
                getView().openItemDetail(listableItemType, offer.getName());
            }

            @Override
            public void showEmpty() {

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
        void showList(ListableItemType listableItemType, List<ListableItem> listableItems);

        void openItemDetail(ListableItemType itemType, String name);
    }
}
