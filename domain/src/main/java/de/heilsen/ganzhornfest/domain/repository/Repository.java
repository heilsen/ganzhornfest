package de.heilsen.ganzhornfest.domain.repository;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Offer;

public interface Repository<T> {
    List<T> getAll();

    T get(int i);

    T get(String name) throws EntityNotFoundException;
}
