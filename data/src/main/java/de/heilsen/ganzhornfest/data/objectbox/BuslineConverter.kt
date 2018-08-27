package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Busline

object BuslineConverter {
    fun from(buslineEntity: BuslineEntity): Busline {
        return Busline(buslineEntity.direction, buslineEntity.description)
    }

    fun from(buslineEntityList: List<BuslineEntity>): List<Busline> {
        return buslineEntityList.map { buslineEntity: BuslineEntity -> from(buslineEntity) }
    }
}