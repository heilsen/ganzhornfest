package de.heilsen.ganzhornfest.domain.interactor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException;
import de.heilsen.ganzhornfest.domain.repository.Repository;

import static java.util.Collections.emptyList;

public class OfferInfoInteractor {

    private final Repository<Food> foodRepository;
    private final Repository<Drink> drinkRepository;
    private final Repository<ActionableOffer> actionableOfferRepository;
    private Repository<Club> clubRepository;

    public OfferInfoInteractor(Repository<Club> clubRepository, Repository<Food> foodRepository,
                               Repository<Drink> drinkRepository,
                               Repository<ActionableOffer> actionableOfferRepository) {
        this.clubRepository = clubRepository;
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.actionableOfferRepository = actionableOfferRepository;
    }

    public void showInfo(OfferType offerType, String itemName, OfferInfoInteractor.Callback callback) {
        List<Club> clubList = clubRepository.getAll();
        List<Club> clubsWithOfferList = new ArrayList<>();
        Offer offer;
        try {
            switch (offerType) {
                case FOOD:
                    offer = foodRepository.get(itemName);
                    break;
                case DRINK:
                    offer = drinkRepository.get(itemName);
                    break;
                case ACTIONABLE_OFFER:
                    offer = actionableOfferRepository.get(itemName);
                    break;
                default:
                    offer = new Offer("", "");
            }

            clubLoop:
            for (Club club : clubList) {
                List<? extends Offer> offerList;
                switch (offerType) {
                    case FOOD:
                        offerList = club.getFoodList();
                        break;
                    case DRINK:
                        offerList = club.getDrinkList();
                        break;
                    case ACTIONABLE_OFFER:
                        offerList = club.getActionableOfferList();
                        break;
                    default:
                        offerList = emptyList();
                }
                for (Offer offerItem : offerList) {
                    if (offerItem.getName().equals(itemName)) {
                        clubsWithOfferList.add(club);
                        continue clubLoop;
                    }
                }
            }
            callback.show(offer, sort(clubsWithOfferList));
        } catch (EntityNotFoundException e) {
            callback.showEmpty();
        }
    }

    //TODO: move sort out of here. It should be in the presenter
    private static List<Club> sort(List<Club> list) {
        //noinspection Java8ListSort, doesn't work for API<24
        Collections.sort(list, new Comparator<Club>() {
            @Override
            public int compare(Club club1, Club club2) {
                return club1.getName().compareTo(club2.getName());
            }
        });
        return list;
    }

    public interface Callback {
        void show(Offer offer, List<Club> clubList);

        void showEmpty();
    }

}
