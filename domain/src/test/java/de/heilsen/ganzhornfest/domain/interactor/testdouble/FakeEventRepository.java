package de.heilsen.ganzhornfest.domain.interactor.testdouble;


import java.util.Arrays;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Event;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class FakeEventRepository implements Repository<Event> {

    private List<Event> Events;

    public FakeEventRepository(Event... EventsArray) {
        Events = Arrays.asList(EventsArray);
    }

    @Override
    public List<Event> getAll() {
        return Events;
    }

    @Override
    public Event get(int id) {
        return Events.get(id);
    }

    @Override
    public Event get(String description) {
        for (Event Event : Events) {
            if(Event.getDescription().equals(description)) {
                return Event;
            }
        }
        return null;
    }

}
