package de.heilsen.ganzhornfest.domain.interactor.testdouble;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Offer;
import de.heilsen.ganzhornfest.domain.entity.OfferType;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeClubRepository implements Repository<Club> {

    private List<Club> clubs;

    public FakeClubRepository(Club... clubsArray) {
        clubs = Arrays.asList(clubsArray);
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
        for (Club club : clubs) {
            if(club.getName().equals(name)) {
                return club;
            }
        }
        return null;
    }

}
