package de.heilsen.ganzhornfest.interactor.testdouble;


import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.Club;
import de.heilsen.ganzhornfest.repository.ClubRepository;

public class FakeClubRepository implements ClubRepository {

    private List<Club> clubs;

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


}
