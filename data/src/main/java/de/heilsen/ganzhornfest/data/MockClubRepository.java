package de.heilsen.ganzhornfest.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Club;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class MockClubRepository implements Repository<Club> {
    private static final int RANDOM_NUMBER = 1;

    private List<Club> clubs;

    public MockClubRepository(int listSize) {
        clubs = new ArrayList<>();
        clubs.addAll(Arrays.asList(new Club("Club 1", "Club 1 is awesome"), new Club("Club 2")
                , new Club("3", "tada")));
        for (int i = 4; i < listSize; i++) {
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

    @Override
    public Club get(String name) {
        return clubs.get(RANDOM_NUMBER);
    }

    public static class MockFoodRepository implements Repository<Food> {
        private static final int RANDOM_NUMBER = 1;

        private List<Food> foods;

        public MockFoodRepository(int listSize) {
            foods = new ArrayList<>();
            foods.addAll(Arrays.asList(new Food("Food 1", "Food 1 is awesome"), new Food("Food 2")
                    , new Food("3", "tada")));
            for (int i = 4; i < listSize; i++) {
                foods.add(new Food("Food " + i, "Food " + i + " is awesome"));
            }
        }

        @Override
        public List<Food> getAll() {
            return foods;
        }

        @Override
        public Food get(int i) {
            return foods.get(i);
        }

        @Override
        public Food get(String name) {
            return foods.get(RANDOM_NUMBER);
        }

        public void set(List<Food> foods) {
            this.foods = foods;
        }

    }
}
