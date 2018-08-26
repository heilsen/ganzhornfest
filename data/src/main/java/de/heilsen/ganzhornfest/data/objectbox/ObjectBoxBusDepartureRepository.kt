package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.BusDeparture
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore
import java.util.*

val amorbach = BuslineEntity("Amorbach")

class ObjectBoxBusDepartureRepository(boxStore: BoxStore) : Repository<BusDeparture> {
    internal var box: Box<BusDepartureEntity> = boxStore.boxFor(BusDepartureEntity::class.java)

    internal val b1 = BusDepartureEntity(
            Date(118, 8, 1, 16, 0).time)
    internal val b2 = BusDepartureEntity(
            Date(118, 8, 1, 17, 0).time)

    init {
        prefill()
    }

    fun prefill() {
        box.removeAll()
        b1.busline.target = amorbach
        b2.busline.target = amorbach
        box.put(
                b1,
                b2
        )
    }

    override fun getAll(): List<BusDeparture> {
        return box.all.map { BusDepartureConverter.from(it) }
    }

    override fun get(i: Int): BusDeparture? {
        return BusDepartureConverter.from(box.get(i.toLong()))
    }

    @Throws(EntityNotFoundException::class)
    override fun get(name: String): BusDeparture? {
        throw EntityNotFoundException()
    }
}
