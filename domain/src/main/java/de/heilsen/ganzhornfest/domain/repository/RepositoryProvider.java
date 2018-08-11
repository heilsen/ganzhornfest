package de.heilsen.ganzhornfest.domain.repository;

import java.util.HashMap;
import java.util.Map;

import de.heilsen.ganzhornfest.domain.entity.ListableItem;
import de.heilsen.ganzhornfest.domain.entity.ListableItemType;

public class RepositoryProvider {
    private Map<Class, Repository<? extends ListableItem>> map; //FIXME: key should be super class of Listable Item

    public RepositoryProvider() {
        this.map = new HashMap<>();
    }

    public void addRepository(Class aClass, Repository<? extends ListableItem> repo) {
        map.put(aClass, repo);
    }

    public Repository<? extends ListableItem> getRepository(ListableItemType type) {
        Class itemClass = type.getItemClass();
        return map.get(itemClass);
    }
}
