package de.heilsen.ganzhornfest.repository;

import java.util.List;

import de.heilsen.ganzhornfest.Club;

public interface ClubRepository {
    List<Club> getAll();

    Club get(int i);
}
