package de.heilsen.ganzhornfest.app.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.app.data.MyObjectBox;
import de.heilsen.ganzhornfest.app.data.ObjectBoxActionOfferRepository;
import de.heilsen.ganzhornfest.app.data.ObjectBoxClubRepository;
import de.heilsen.ganzhornfest.app.data.ObjectBoxDrinkRepository;
import de.heilsen.ganzhornfest.app.data.ObjectBoxFoodRepository;
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
import io.objectbox.BoxStore;

@Module
public class ComponentModule {

    private Context context;

    public ComponentModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public ClubListInteractor getListInteractor(Repository<Club> clubRepository) {
        return new ThreadedClubListInteractor(clubRepository);
    }

    @Provides
    @Singleton
    public ListPresenter listableItemsPresenter(ClubListInteractor clubListInteractor,
                                                OfferListInteractor offerListInteractor) {
        return new ListPresenter(clubListInteractor, offerListInteractor);
    }

    @Provides
    @Singleton
    public ClubInfoInteractor clubInfoInteractor(Repository<Club> clubRepository) {
        return new ThreadedClubInfoInteractor(clubRepository);
    }

    @Provides
    @Singleton
    public OfferInfoInteractor getItemInfoInteractor(Repository<Club> clubRepository, Repository<Food> foodRepository,
                                                     Repository<Drink> drinkRepository,
                                                     Repository<ActionableOffer> actionableOfferRepository) {
        return new ThreadedOfferInfoInteractor(clubRepository, foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Provides
    @Singleton
    public OfferListInteractor offerListInteractor(Repository<Food> foodRepository, Repository<Drink> drinkRepository,
                                                   Repository<ActionableOffer> actionableOfferRepository) {
        return new ThreadedOfferListInteractor(foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Provides
    @Singleton
    public ListableItemAdapter listableItemAdapter() {
        return new ListableItemAdapter();
    }

    @Provides
    @Singleton
    public DetailPresenter detailPresenter(ClubInfoInteractor clubInfoInteractor,
                                           OfferInfoInteractor offerInfoInteractor) {
        return new DetailPresenter(clubInfoInteractor, offerInfoInteractor);
    }

    @Provides
    @Singleton
    public BoxStore boxStore() {
        return MyObjectBox.builder().androidContext(context).build();
    }

    @Provides
    @Singleton
    public ObjectBoxDrinkRepository objectBoxDrinkRepository(BoxStore boxStore) {
        return new ObjectBoxDrinkRepository(boxStore);
    }

    @Provides
    @Singleton
    public Repository<Drink> drinkRepository(ObjectBoxDrinkRepository objectBoxDrinkRepository) {
        return objectBoxDrinkRepository;
    }

    @Provides
    @Singleton
    public ObjectBoxFoodRepository objectBoxFoodRepository(BoxStore boxStore) {
        return new ObjectBoxFoodRepository(boxStore);
    }

    @Provides
    @Singleton
    public Repository<Food> foodRepository(ObjectBoxFoodRepository objectBoxFoodRepository) {
        return objectBoxFoodRepository;
    }

    @Provides
    @Singleton
    public ObjectBoxActionOfferRepository objectBoxActionOfferRepository(BoxStore boxStore) {
        return new ObjectBoxActionOfferRepository(boxStore);
    }

    @Provides
    @Singleton
    public Repository<ActionableOffer> offerRepository(ObjectBoxActionOfferRepository objectBoxActionOfferRepository) {
        return objectBoxActionOfferRepository;
    }

    @Provides
    @Singleton
    public ObjectBoxClubRepository objectBoxClubRepository(BoxStore boxStore,
                                                           ObjectBoxFoodRepository objectBoxFoodRepository,
                                                           ObjectBoxDrinkRepository objectBoxDrinkRepository,
                                                           ObjectBoxActionOfferRepository objectBoxActionOfferRepository) {
        return new ObjectBoxClubRepository(boxStore, objectBoxFoodRepository, objectBoxDrinkRepository, objectBoxActionOfferRepository);
    }

    @Provides
    @Singleton
    public Repository<Club> clubRepository(ObjectBoxClubRepository objectBoxClubRepository) {
        return objectBoxClubRepository;
    }


}
