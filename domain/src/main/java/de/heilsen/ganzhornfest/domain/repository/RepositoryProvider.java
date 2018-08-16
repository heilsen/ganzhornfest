package de.heilsen.ganzhornfest.domain.repository;

import de.heilsen.ganzhornfest.domain.entity.Offer;

public interface RepositoryProvider<T> {
    Repository<Offer> getRepository(Class<? extends T> tClass);

    boolean hasRepository(Class<? extends T> tClass);
}
