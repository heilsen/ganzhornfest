package de.heilsen.ganzhornfest.data.fake;

import java.util.Collections;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeActionableOfferRepository implements Repository<ActionableOffer> {
    @Override
    public List<ActionableOffer> getAll() {
        return Collections.singletonList(
                new ActionableOffer("Schnappfalle")
        );
    }

    @Override
    public ActionableOffer get(int i) {
        return new ActionableOffer("Schnappfalle");

    }

    @Override
    public ActionableOffer get(String name) {
        return new ActionableOffer("Schnappfalle");
    }
}
