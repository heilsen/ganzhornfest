package de.heilsen.ganzhornfest.data.fake;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Event;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeEventRepository implements Repository<Event> {
    @Override
    public List<Event> getAll() {
        return Collections.singletonList(
                new Event("Eröffnung", 0)
        );
    }

    @Override
    public Event get(int i) {
        return new Event("Musik", new Date(2018,8,1,16,17,0).getTime());

    }

    @Override
    public Event get(String name) {
        return new Event("Musik", new Date(2018,8,1,16,17,0).getTime());
    }
}
