package de.heilsen.ganzhornfest.domain.interactor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.heilsen.ganzhornfest.domain.entity.PointInTime;
import de.heilsen.ganzhornfest.domain.repository.Repository;

public class ListPointInTimeInteractor {

    private Repository<? extends PointInTime> pointInTimeRepository;

    public ListPointInTimeInteractor(Repository<? extends PointInTime> pointInTimeRepository) {
        this.pointInTimeRepository = pointInTimeRepository;
    }

    public void showForDate(Calendar date, Callback callback) {
        List<PointInTime> pointInTimeList = new ArrayList<>(pointInTimeRepository.getAll());
        callback.show(filterByDate(pointInTimeList, date));
    }

    private List<PointInTime> filterByDate(List<PointInTime> pointInTimeList, Calendar date) {
        List<PointInTime> filteredByDate = new ArrayList<>();
        for (PointInTime pointInTime : pointInTimeList) {
            if (hasSameDate(date, pointInTime)) {
                filteredByDate.add(pointInTime);
            }
        }
        return filteredByDate;
    }

    private boolean hasSameDate(Calendar givenDate, PointInTime pointInTime) {
        long startDate = pointInTime.getTime();
        Calendar pointInTimeDate = Calendar.getInstance();
        pointInTimeDate.setTimeInMillis(startDate);
        return givenDate.get(Calendar.YEAR) == pointInTimeDate.get(Calendar.YEAR) &&
               givenDate.get(Calendar.MONTH) == pointInTimeDate.get(Calendar.MONTH) &&
               givenDate.get(Calendar.DATE) == pointInTimeDate.get(Calendar.DATE);
    }

    public interface Callback {
        void show(List<PointInTime> pointInTimeList);
    }
}
