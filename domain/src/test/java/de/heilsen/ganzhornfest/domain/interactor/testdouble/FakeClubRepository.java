package de.heilsen.ganzhornfest.domain.interactor.testdouble;


import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeClubRepository implements Repository<Club> {

    private List<Club> clubs;
    private static final int RANDOM_NUMBER = 1;

    public FakeClubRepository() {
        clubs = Arrays.asList(
                new Club("Club Soccer", "Club 1 is awesome"),
                new Club("Lions Club"),
                new Club("St. Paulus", "Katholischer Jugendclub, jetzt nur noch Club"),
                new Club("Kolping"));
    }

    @Override
    public List<Club> getAll() {
        return clubs;
    }

    @Override
    public Club get(int id) {
        return clubs.get(id);
    }

    @Override
    public Club get(String name) {
        return clubs.get(RANDOM_NUMBER);
    }


}
