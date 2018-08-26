package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Event

object EventConverter {
    fun from(eventEntity: EventEntity): Event {
        return Event(eventEntity.description, eventEntity.startDate)
    }

    fun from(eventEntityList: List<EventEntity>): List<Event> {
        return eventEntityList.map { eventEntity: EventEntity -> from(eventEntity) }
    }
}