package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.BusDeparture
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore
import java.util.*

class ObjectBoxBusDepartureRepository(boxStore: BoxStore,
                                      private val busLineRepository: ObjectBoxBusLineRepository) : Repository<BusDeparture> {
    internal var box: Box<BusDepartureEntity> = boxStore.boxFor(BusDepartureEntity::class.java)

    internal val a1 = BusDepartureEntity(Date(118, 8, 1, 19, 33).time)
    internal val a2 = BusDepartureEntity(Date(118, 8, 1, 20, 18).time)
    internal val a3 = BusDepartureEntity(Date(118, 8, 1, 21, 18).time)
    internal val a4 = BusDepartureEntity(Date(118, 8, 1, 22, 18).time)
    internal val a5 = BusDepartureEntity(Date(118, 8, 1, 22, 48).time)
    internal val a6 = BusDepartureEntity(Date(118, 8, 1, 23, 18).time)
    internal val a7 = BusDepartureEntity(Date(118, 8, 1, 23, 45).time)
    internal val a8 = BusDepartureEntity(Date(118, 8, 2, 0, 45).time)

    internal val a9 = BusDepartureEntity(Date(118, 8, 2, 19, 33).time)
    internal val a10 = BusDepartureEntity(Date(118, 8, 2, 19, 48).time)
    internal val a11 = BusDepartureEntity(Date(118, 8, 2, 20, 10).time)
    internal val a12 = BusDepartureEntity(Date(118, 8, 2, 20, 48).time)
    internal val a13 = BusDepartureEntity(Date(118, 8, 2, 21, 12).time)
    internal val a14 = BusDepartureEntity(Date(118, 8, 2, 21, 48).time)
    internal val a15 = BusDepartureEntity(Date(118, 8, 2, 22, 45).time)
    internal val a16 = BusDepartureEntity(Date(118, 8, 2, 22, 48).time)
    internal val a17 = BusDepartureEntity(Date(118, 8, 2, 23,45).time)
    internal val a18 = BusDepartureEntity(Date(118, 8, 3, 0,45).time)

    internal val a19 = BusDepartureEntity(Date(118, 8, 3, 19, 33).time)
    internal val a20 = BusDepartureEntity(Date(118, 8, 3, 19, 48).time)
    internal val a21 = BusDepartureEntity(Date(118, 8, 3, 20, 18).time)
    internal val a22 = BusDepartureEntity(Date(118, 8, 3, 20, 48).time)
    internal val a23 = BusDepartureEntity(Date(118, 8, 3, 21, 18).time)
    internal val a24 = BusDepartureEntity(Date(118, 8, 3, 21, 48).time)
    internal val a25 = BusDepartureEntity(Date(118, 8, 3, 22, 18).time)
    internal val a26 = BusDepartureEntity(Date(118, 8, 3, 22, 48).time)
    internal val a27 = BusDepartureEntity(Date(118, 8, 3, 23, 18).time)
    internal val a28 = BusDepartureEntity(Date(118, 8, 3, 23, 48).time)
    internal val a29 = BusDepartureEntity(Date(118, 8, 4, 0, 45).time)

    init {
        prefill()
    }

    fun prefill() {
        box.removeAll()
        val amorbachTimes : List<BusDepartureEntity> = listOf(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,a25,a26,a27,a28,a29)
        amorbachTimes.forEach { it.busline.target = busLineRepository.amorbach }
        box.put(
                amorbachTimes
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
