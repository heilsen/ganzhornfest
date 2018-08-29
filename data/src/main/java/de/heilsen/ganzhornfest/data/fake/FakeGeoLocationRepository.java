package de.heilsen.ganzhornfest.data.fake;

import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.GeoLocation;
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeGeoLocationRepository implements Repository<GeoLocation> {
    @Override
    public List<GeoLocation> getAll() {
        return null;
    }

    @Override
    public GeoLocation get(int i) {
        return null;
    }

    @Override
    public GeoLocation get(String name) throws EntityNotFoundException {
        throw new EntityNotFoundException();
    }
}
