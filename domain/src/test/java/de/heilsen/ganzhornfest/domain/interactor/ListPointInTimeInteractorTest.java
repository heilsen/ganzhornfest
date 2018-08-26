package de.heilsen.ganzhornfest.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Calendar;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.Event;
import de.heilsen.ganzhornfest.domain.entity.PointInTime;
import de.heilsen.ganzhornfest.domain.interactor.testdouble.FakeEventRepository;
import de.heilsen.ganzhornfest.domain.repository.Repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.verify;

public class ListPointInTimeInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private Repository<Event> eventRepository;
    private ListPointInTimeInteractor listPointInTimeInteractor;
    @Mock
    private ListPointInTimeInteractor.Callback callback;
    @Captor
    private ArgumentCaptor<List<PointInTime>> captor;
    private Event first;
    private Event second;

    @Before
    public void setUp() throws Exception {
        first = new Event("first", createTimestamp(2018, 8, 1, 16, 0));
        second = new Event("second", createTimestamp(2018, 8, 1, 20, 15));
        eventRepository = new FakeEventRepository(
                first,
                second,
                new Event("third", createTimestamp(2018, 8, 2, 20, 0)),
                new Event("fourth", createTimestamp(2018, 8, 3, 20, 0)),
                new Event("old", createTimestamp(2017, 9, 3, 20, 0))
        );
        listPointInTimeInteractor = new ListPointInTimeInteractor(eventRepository);
    }

    private long createTimestamp(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }

    @Test
    public void showEventsForDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.SEPTEMBER, 1);

        listPointInTimeInteractor.showForDate(calendar, callback);
        verify(callback).show(captor.capture());

        assertThat(captor.getValue(), containsInAnyOrder(first, second));
    }
}