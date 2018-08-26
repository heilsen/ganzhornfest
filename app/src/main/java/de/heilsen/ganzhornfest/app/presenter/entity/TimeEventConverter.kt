package de.heilsen.ganzhornfest.app.presenter.entity

import de.heilsen.ganzhornfest.domain.entity.BusDeparture
import de.heilsen.ganzhornfest.domain.entity.Event
import de.heilsen.ganzhornfest.domain.entity.PointInTime
import java.util.*

object TimeEventConverter {

    fun from(pointInTime: PointInTime): TimeEvent {
        return TimeEvent(timeFormatter(pointInTime.time), pointInTime.description, null)
    }

    @JvmStatic
    fun from(pointInTimes: List<PointInTime>): List<TimeEvent> {
        return pointInTimes.map { pointInTime: PointInTime -> from(pointInTime) }
    }

    fun from(event: Event): TimeEvent {
        return TimeEvent(timeFormatter(event.time), event.description, null)
    }

    fun from(busDeparture: BusDeparture): TimeEvent {
        return TimeEvent(timeFormatter(busDeparture.time), busDeparture.description, null)
    }

    private fun timeFormatter(time: Long): String {
        //TODO: replace me with a dateFormatter
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return "%tR".format(calendar)
    }

}