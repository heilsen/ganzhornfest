package de.heilsen.ganzhornfest.app.presenter;

import java.util.Calendar;
import java.util.List;

import de.heilsen.ganzhornfest.app.presenter.entity.TimeEvent;
import de.heilsen.ganzhornfest.app.presenter.entity.TimeEventConverter;
import de.heilsen.ganzhornfest.domain.entity.PointInTime;
import de.heilsen.ganzhornfest.domain.interactor.ListPointInTimeInteractor;

public class ListPointInTimePresenter extends Presenter<ListPointInTimePresenter.View> {
    private ListPointInTimeInteractor listPointInTimeInteractor;

    public ListPointInTimePresenter(ListPointInTimeInteractor listPointInTimeInteractor) {
        this.listPointInTimeInteractor = listPointInTimeInteractor;
    }

    public void showEventsForDate(Calendar date) {
        listPointInTimeInteractor.showForDate(date, new ListPointInTimeInteractor.Callback() {
            @Override
            public void show(List<PointInTime> pointInTimeList) {
                getView().showEvents(TimeEventConverter.from(pointInTimeList));
            }
        });
    }

    public interface View extends Presenter.View {
        void showEvents(List<TimeEvent> timeEventList);
    }

}
