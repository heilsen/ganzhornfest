package de.heilsen.ganzhornfest.domain.interactor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class OfferListInteractor {

    private final Repository<Food> foodRepository;
    private final Repository<Drink> drinkRepository;
    private final Repository<ActionableOffer> actionableOfferRepository;

    public OfferListInteractor(Repository<Food> foodRepository,
                               Repository<Drink> drinkRepository,
                               Repository<ActionableOffer> actionableOfferRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.actionableOfferRepository = actionableOfferRepository;
    }

    public void listOffer(OfferType offerType, Callback callback) {
        switch (offerType) {
            case FOOD:
                callback.showOfferList(sort(new ArrayList<>(foodRepository.getAll())));
                break;
            case DRINK:
                callback.showOfferList(sort(new ArrayList<>(drinkRepository.getAll())));
                break;
            case ACTIONABLE_OFFER:
                callback.showOfferList(sort(new ArrayList<>(actionableOfferRepository.getAll())));
                break;
            case UNKNOWN:
            default:
                callback.showOfferList(Collections.emptyList());
        }
    }

    private static List<Offer> sort(List<Offer> list) {
        //noinspection Java8ListSort, doesn't work for API<24
        Collections.sort(list, new Comparator<Offer>() {
            @Override
            public int compare(Offer Offer1, Offer Offer2) {
                return Offer1.getName().compareTo(Offer2.getName());
            }
        });
        return list;
    }

    public void selectOffer(OfferType offerType, String name, DetailCallback callback) {
        try {
            switch (offerType) {
                case FOOD:
                    callback.showOfferDetail(foodRepository.get(name));
                    break;
                case DRINK:
                    callback.showOfferDetail(drinkRepository.get(name));
                    break;
                case ACTIONABLE_OFFER:
                    callback.showOfferDetail(actionableOfferRepository.get(name));
                    break;
                case UNKNOWN:
                default:
                    callback.showOfferDetail(new Offer("", ""));
            }
        } catch (EntityNotFoundException e) {
            callback.showEmpty();
        }
    }

    public interface DetailCallback {
        void showOfferDetail(Offer offer);

        void showEmpty();
    }

    public interface Callback {
        void showOfferList(List<Offer> offerList);
    }

}