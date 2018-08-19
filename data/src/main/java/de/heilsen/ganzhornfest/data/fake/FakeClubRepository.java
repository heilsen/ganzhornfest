package de.heilsen.ganzhornfest.data.fake;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeClubRepository implements Repository<Club> {
    private Club stpc = new Club("St. Paulus Club", "",
            Arrays.asList(new Food("K#sspatzen")),
            Arrays.asList(new Drink("Cola"), new Drink("Fanta")));
    @Override
    public List<Club> getAll() {
        return Arrays.asList(
                new Club("Fußball Club", ""),
                new Club("TT2"),
                stpc,
                new Club("Kolpingfamilie", "", Collections.singletonList(new Food("Hacksteak"))),
                new Club("Lions Club", "", Collections.singletonList(new Food("Hacksteak")))
        );
    }

    @Override
    public Club get(int i) {
        return stpc;

    }

    @Override
    public Club get(String name) {
        return stpc;
    }
}
