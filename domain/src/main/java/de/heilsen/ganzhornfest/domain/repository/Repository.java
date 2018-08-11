package de.heilsen.ganzhornfest.domain.repository;

import java.util.List;

public interface Repository<T> {
    List<T> getAll();

    T get(int i);

    T get(String name);
}
