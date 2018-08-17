package de.heilsen.ganzhornfest.data.di;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public interface RepositoryComponent {
    Repository<Club> clubRepository();

    Repository<Food> foodRepository();

    Repository<Drink> drinkRepository();

    Repository<ActionableOffer> actionableOfferRepository();
}
