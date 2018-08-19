package de.heilsen.ganzhornfest.data.fake;

import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Drink;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeDrinkRepository implements Repository<Drink> {
    @Override
    public List<Drink> getAll() {
        return Arrays.asList(
                new Drink("Cola"),
                new Drink("Fanta"),
                new Drink("Bier (Pils)"),
                new Drink("Schnapps"),
                new Drink("Fritz-Cola")
        );
    }

    @Override
    public Drink get(int i) {
        return new Drink("Fritz-Cola");

    }

    @Override
    public Drink get(String name) {
        return new Drink("Fritz-Cola");
    }
}
