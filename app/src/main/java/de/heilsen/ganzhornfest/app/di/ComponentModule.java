package de.heilsen.ganzhornfest.app.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.app.interactor.ThreadedClubInfoInteractor;
import de.heilsen.ganzhornfest.app.interactor.ThreadedClubListInteractor;
import de.heilsen.ganzhornfest.app.interactor.ThreadedOfferInfoInteractor;
import de.heilsen.ganzhornfest.app.interactor.ThreadedOfferListInteractor;
import de.heilsen.ganzhornfest.app.presenter.DetailPresenter;
import de.heilsen.ganzhornfest.app.presenter.ListPresenter;
import de.heilsen.ganzhornfest.app.ui.recyclerview.ListableItemAdapter;
import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.interactor.ClubInfoInteractor;
import de.heilsen.ganzhornfest.domain.interactor.ClubListInteractor;
import de.heilsen.ganzhornfest.domain.interactor.OfferInfoInteractor;
import de.heilsen.ganzhornfest.domain.interactor.OfferListInteractor;
import de.heilsen.ganzhornfest.domain.repository.Repository;

@Module
public class ComponentModule {

    @Provides
    @Singleton
    public static ClubListInteractor getListInteractor(Repository<Club> clubRepository) {
        return new ThreadedClubListInteractor(clubRepository);
    }

    @Provides
    @Singleton
    public static ListPresenter listableItemsPresenter(ClubListInteractor clubListInteractor,
                                                OfferListInteractor offerListInteractor) {
        return new ListPresenter(clubListInteractor, offerListInteractor);
    }

    @Provides
    @Singleton
    public static ClubInfoInteractor clubInfoInteractor(Repository<Club> clubRepository) {
        return new ThreadedClubInfoInteractor(clubRepository);
    }

    @Provides
    @Singleton
    public static OfferInfoInteractor getItemInfoInteractor(Repository<Club> clubRepository, Repository<Food> foodRepository,
                                                     Repository<Drink> drinkRepository,
                                                     Repository<ActionableOffer> actionableOfferRepository) {
        return new ThreadedOfferInfoInteractor(clubRepository, foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Provides
    @Singleton
    public static OfferListInteractor offerListInteractor(Repository<Food> foodRepository, Repository<Drink> drinkRepository,
                                                   Repository<ActionableOffer> actionableOfferRepository) {
        return new ThreadedOfferListInteractor(foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Provides
    @Singleton
    public static ListableItemAdapter listableItemAdapter() {
        return new ListableItemAdapter();
    }

    @Provides
    @Singleton
    public static DetailPresenter detailPresenter(ClubInfoInteractor clubInfoInteractor,
                                           OfferInfoInteractor offerInfoInteractor) {
        return new DetailPresenter(clubInfoInteractor, offerInfoInteractor);
    }

}
