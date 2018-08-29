package de.heilsen.ganzhornfest.data.di;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.data.fake.FakeActionableOfferRepository;
import de.heilsen.ganzhornfest.data.fake.FakeBusDepartureRepository;
import de.heilsen.ganzhornfest.data.fake.FakeClubRepository;
import de.heilsen.ganzhornfest.data.fake.FakeDrinkRepository;
import de.heilsen.ganzhornfest.data.fake.FakeEventRepository;
import de.heilsen.ganzhornfest.data.fake.FakeFoodRepository;
import de.heilsen.ganzhornfest.data.fake.FakeGeoLocationRepository;
import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.BusDeparture;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Event;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.GeoLocation;
import de.heilsen.ganzhornfest.domain.repository.Repository;

@Module
public class FakeRepositoryModule {
    @Provides
    @RepositoryScope
    public Repository<Club> clubRepository() {
        return new FakeClubRepository();
    }

    @Provides
    @RepositoryScope
    public Repository<Food> foodRepository() {
        return new FakeFoodRepository();
    }

    @Provides
    @RepositoryScope
    public Repository<Drink> drinkRepository() {
        return new FakeDrinkRepository();
    }

    @Provides
    @RepositoryScope
    public Repository<ActionableOffer> actionableOfferRepository() {
        return new FakeActionableOfferRepository();
    }

    @Provides
    @RepositoryScope
    public Repository<Event> EventRepository() {
        return new FakeEventRepository();
    }
    @Provides
    @RepositoryScope
    public Repository<BusDeparture> BusDepartureRepository() {
        return new FakeBusDepartureRepository();
    }

    @Provides
    @RepositoryScope
    public Repository<GeoLocation> geoLocationRepository() {
        return new FakeGeoLocationRepository();
    }
}
