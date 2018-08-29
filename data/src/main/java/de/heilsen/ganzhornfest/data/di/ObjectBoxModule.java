package de.heilsen.ganzhornfest.data.di;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.data.objectbox.MyObjectBox;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxActionOfferRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxBusDepartureRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxBusLineRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxClubRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxDrinkRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxEventRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxFoodRepository;
import de.heilsen.ganzhornfest.data.objectbox.ObjectBoxGeoLocationRepository;
import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.BusDeparture;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Event;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.GeoLocation;
import de.heilsen.ganzhornfest.domain.repository.Repository;
import io.objectbox.BoxStore;

@Module
public class ObjectBoxModule {
    private Context context;

    public ObjectBoxModule(Context context) {
        this.context = context;
    }

    @Provides
    @RepositoryScope
    public BoxStore boxStore() {
        return MyObjectBox.builder().androidContext(context).build();
    }

    @Provides
    @RepositoryScope
    public ObjectBoxDrinkRepository objectBoxDrinkRepository(BoxStore boxStore) {
        return new ObjectBoxDrinkRepository(boxStore);
    }

    @Provides
    @RepositoryScope
    public Repository<Drink> drinkRepository(ObjectBoxDrinkRepository objectBoxDrinkRepository) {
        return objectBoxDrinkRepository;
    }

    @Provides
    @RepositoryScope
    public ObjectBoxFoodRepository objectBoxFoodRepository(BoxStore boxStore) {
        return new ObjectBoxFoodRepository(boxStore);
    }

    @Provides
    @RepositoryScope
    public Repository<Food> foodRepository(ObjectBoxFoodRepository objectBoxFoodRepository) {
        return objectBoxFoodRepository;
    }

    @Provides
    @RepositoryScope
    public ObjectBoxActionOfferRepository objectBoxActionOfferRepository(BoxStore boxStore) {
        return new ObjectBoxActionOfferRepository(boxStore);
    }

    @Provides
    @RepositoryScope
    public Repository<ActionableOffer> offerRepository(ObjectBoxActionOfferRepository objectBoxActionOfferRepository) {
        return objectBoxActionOfferRepository;
    }

    @Provides
    @RepositoryScope
    public ObjectBoxClubRepository objectBoxClubRepository(BoxStore boxStore,
                                                           ObjectBoxFoodRepository objectBoxFoodRepository,
                                                           ObjectBoxDrinkRepository objectBoxDrinkRepository,
                                                           ObjectBoxActionOfferRepository objectBoxActionOfferRepository,
                                                           ObjectBoxGeoLocationRepository objectBoxGeoLocationRepository) {
        return new ObjectBoxClubRepository(boxStore,
                objectBoxFoodRepository,
                objectBoxDrinkRepository,
                objectBoxActionOfferRepository,
                objectBoxGeoLocationRepository);
    }

    @Provides
    @RepositoryScope
    public Repository<Club> clubRepository(ObjectBoxClubRepository objectBoxClubRepository) {
        return objectBoxClubRepository;
    }

    @Provides
    @RepositoryScope
    public Repository<Event> eventRepository(BoxStore boxStore) {
        return new ObjectBoxEventRepository(boxStore);
    }

    @Provides
    @RepositoryScope
    public ObjectBoxBusLineRepository busLineRepository(BoxStore boxStore) {
        return new ObjectBoxBusLineRepository(boxStore);
    }

    @Provides
    @RepositoryScope
    public Repository<BusDeparture> busDepartureRepository(BoxStore boxStore, ObjectBoxBusLineRepository objectBoxBusLineRepository) {
        return new ObjectBoxBusDepartureRepository(boxStore, objectBoxBusLineRepository);
    }

    @Provides
    @RepositoryScope
    public ObjectBoxGeoLocationRepository geoLocationRepository(BoxStore boxStore) {
        return new ObjectBoxGeoLocationRepository(boxStore);
    }
}
