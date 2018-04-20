package de.heilsen.ganzhornfest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.repository.ClubRepository;

public class FakeClubRepository implements ClubRepository {

    private final int LIST_SIZE = 5;
    private List<Club> clubs;

    public FakeClubRepository() {
        clubs = new ArrayList<>();
        clubs.addAll(Arrays.asList(new Club("Club 1", "Club 1 is awesome"), new Club("Club 2")
                , new Club("3", "tada")));
        for (int i = 4; i < LIST_SIZE; i++) {
            clubs.add(new Club("Club " + i, "Club " + i + " is awesome"));
        }
    }

    @Override
    public List<Club> getAll() {
        return clubs;
    }

    @Override
    public Club get(int i) {
        return clubs.get(i);
    }


    public void set(List<Club> clubs) {
        this.clubs = clubs;
    }

}
