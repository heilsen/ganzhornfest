package de.heilsen.ganzhornfest.domain.interactor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;
import de.heilsen.ganzhornfest.domain.repository.Repository;
import de.heilsen.ganzhornfest.domain.repository.RepositoryProvider;

public class GetListInteractor {
    private RepositoryProvider repositoryProvider;

    public GetListInteractor(RepositoryProvider repositoryProvider) {
        this.repositoryProvider = repositoryProvider;
    }

    public void selectList(ListableItemType type, Callback callback) {
        Repository<? extends ListableItem> repository = repositoryProvider.getRepository(type);
        List<? extends ListableItem> list = repository.getAll();
        //noinspection Java8ListSort (no support in minSdk <= 24),Convert2Lambda
        Collections.sort(list, new Comparator<ListableItem>() {
            @Override
            public int compare(ListableItem listableItem1, ListableItem listableItem2) {
                return listableItem1.getName().compareTo(listableItem2.getName());
            }
        });
        callback.onItemListLoaded(list);
    }

    public void selectItem(ListableItem listableItem, DetailCallback detailCallback) {
        HashMap<ListableItemType, List<? extends ListableItem>> map = new HashMap<>();
        Repository<? extends ListableItem> clubRepository = repositoryProvider.getRepository(ListableItemType.CLUB);
        Repository<? extends ListableItem> foodRepository = repositoryProvider.getRepository(ListableItemType.FOOD);
        map.put(ListableItemType.CLUB, clubRepository.getAll());
        map.put(ListableItemType.FOOD, foodRepository.getAll());
        detailCallback.onItemSelected(map);
    }

    public interface Callback {
        void onItemListLoaded(List<? extends ListableItem> list);
    }

    public interface DetailCallback {
        void onItemSelected(Map<ListableItemType, List<? extends ListableItem>> correspondingItems);
    }
}