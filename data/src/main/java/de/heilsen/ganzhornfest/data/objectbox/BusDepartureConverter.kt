package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.BusDeparture

object BusDepartureConverter {
    fun from(busDepartureEntity: BusDepartureEntity): BusDeparture {
        return BusDeparture(BuslineConverter.from(busDepartureEntity.busline.target), busDepartureEntity.startDate)
    }

    fun from(busDepartureEntityList: List<BusDepartureEntity>): List<BusDeparture> {
        return busDepartureEntityList.map { busDepartureEntity: BusDepartureEntity -> from(busDepartureEntity) }
    }
}