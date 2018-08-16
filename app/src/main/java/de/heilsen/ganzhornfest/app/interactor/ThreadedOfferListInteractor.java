package de.heilsen.ganzhornfest.app.interactor;

import android.os.Handler;
import android.os.Looper;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.interactor.OfferListInteractor;
import de.heilsen.ganzhornfest.domain.repository.Repository;


public class ThreadedOfferListInteractor extends OfferListInteractor {
    public ThreadedOfferListInteractor(
            Repository<Food> foodRepository,
            Repository<Drink> drinkRepository,
            Repository<ActionableOffer> actionableOfferRepository) {
        super(foodRepository, drinkRepository, actionableOfferRepository);
    }

    @Override
    public void listOffer(final OfferType offerType, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ThreadedOfferListInteractor.super.listOffer(offerType, callback);
                    }
                });
            }
        }).run();

    }
}
