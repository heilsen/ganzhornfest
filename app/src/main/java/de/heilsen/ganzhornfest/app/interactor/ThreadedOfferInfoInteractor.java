package de.heilsen.ganzhornfest.app.interactor;


import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.interactor.OfferInfoInteractor;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ThreadedOfferInfoInteractor extends OfferInfoInteractor {
    public ThreadedOfferInfoInteractor(Repository<Club> clubRepository, Repository<Food> foodRepository,
                                       Repository<Drink> drinkRepository,
                                       Repository<ActionableOffer> actionableOfferRepository) {
        super(clubRepository, foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Override
    public void showInfo(final OfferType itemType, final String itemName, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // run in main thread
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            // run in the worker thread
                            @Override
                            public void run() {
                                ThreadedOfferInfoInteractor.super.showInfo(itemType, itemName, callback);
                            }
                        }
                );
            }
        }).run();
    }

}
