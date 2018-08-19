package de.heilsen.ganzhornfest.data.di;

import dagger.Module;
import dagger.Provides;
import de.heilsen.ganzhornfest.data.fake.FakeActionableOfferRepository;
import de.heilsen.ganzhornfest.data.fake.FakeClubRepository;
import de.heilsen.ganzhornfest.data.fake.FakeDrinkRepository;
import de.heilsen.ganzhornfest.data.fake.FakeFoodRepository;
import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
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
}
