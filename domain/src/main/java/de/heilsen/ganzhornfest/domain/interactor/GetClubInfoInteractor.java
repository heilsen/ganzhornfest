package de.heilsen.ganzhornfest.domain.interactor;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class GetClubInfoInteractor {

    private Repository<Club> clubRepository;

    public GetClubInfoInteractor(Repository<Club> clubRepository) {
        this.clubRepository = clubRepository;
    }

    public void showInfo(String clubName, Callback callback) {
        Club club = clubRepository.get(clubName);
        Map<ListableItemType, List<? extends ListableItem>> infoMap = new LinkedHashMap<>();
        infoMap.put(ListableItemType.FOOD, club.getFoodList());
        infoMap.put(ListableItemType.DRINK, club.getDrinkList());
        infoMap.put(ListableItemType.SOME_OTHER_OFFER, club.getSomeOtherOfferList());

        callback.show(club, infoMap);
    }

    public interface Callback {
        void show(Club club, Map<ListableItemType, List<? extends ListableItem>> infoMap);
    }
}
