package de.heilsen.ganzhornfest.data.fake;

import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Food;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeFoodRepository implements Repository<Food> {
    @Override
    public List<Food> getAll() {
        return Arrays.asList(
                new Food("Kässpatzen"),
                new Food("Spitzdappen"),
                new Food("Weißwürste"),
                new Food("Hacksteacks"),
                new Food("Vegetarische Currywurst")
        );
    }

    @Override
    public Food get(int i) {
        return new Food("Hacksteak");

    }

    @Override
    public Food get(String name) {
        return new Food("Hacksteak");
    }
}
