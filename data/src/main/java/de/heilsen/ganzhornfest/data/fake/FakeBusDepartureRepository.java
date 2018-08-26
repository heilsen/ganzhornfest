package de.heilsen.ganzhornfest.data.fake;

import java.util.Collections;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.BusDeparture;
import de.heilsen.ganzhornfest.domain.entity.Busline;
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeBusDepartureRepository implements Repository<BusDeparture> {

    private final BusDeparture busDeparture = new BusDeparture(new Busline("Amorbach", "nach Amorbach"), 0);

    @Override
    public List<BusDeparture> getAll() {
        return Collections.singletonList(busDeparture);
    }

    @Override
    public BusDeparture get(int i) {
        return busDeparture;
    }

    @Override
    public BusDeparture get(String name) throws EntityNotFoundException {
        throw new EntityNotFoundException();
    }
}
