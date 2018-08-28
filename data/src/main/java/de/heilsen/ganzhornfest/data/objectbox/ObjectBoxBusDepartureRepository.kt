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
    internal val a17 = BusDepartureEntity(Date(118, 8, 2, 23, 45).time)
    internal val a18 = BusDepartureEntity(Date(118, 8, 3, 0, 45).time)

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

    internal val b1 = BusDepartureEntity(Date(118, 8, 1, 19, 33).time)
    internal val b2 = BusDepartureEntity(Date(118, 8, 1, 22, 45).time)
    internal val b3 = BusDepartureEntity(Date(118, 8, 1, 23, 18).time)
    internal val b4 = BusDepartureEntity(Date(118, 8, 1, 23, 45).time)
    internal val b5 = BusDepartureEntity(Date(118, 8, 2, 0, 45).time)
    internal val b6 = BusDepartureEntity(Date(118, 8, 2, 1, 28).time)

    internal val b7 = BusDepartureEntity(Date(118, 8, 2, 19, 33).time)
    internal val b8 = BusDepartureEntity(Date(118, 8, 2, 20, 10).time)
    internal val b9 = BusDepartureEntity(Date(118, 8, 2, 21, 12).time)
    internal val b10 = BusDepartureEntity(Date(118, 8, 2, 22, 45).time)
    internal val b11 = BusDepartureEntity(Date(118, 8, 2, 23, 33).time)
    internal val b12 = BusDepartureEntity(Date(118, 8, 2, 23, 45).time)
    internal val b13 = BusDepartureEntity(Date(118, 8, 3, 0, 45).time)

    internal val b14 = BusDepartureEntity(Date(118, 8, 3, 19, 33).time)
    internal val b15 = BusDepartureEntity(Date(118, 8, 3, 19, 48).time)
    internal val b16 = BusDepartureEntity(Date(118, 8, 3, 20, 48).time)
    internal val b17 = BusDepartureEntity(Date(118, 8, 3, 21, 48).time)
    internal val b18 = BusDepartureEntity(Date(118, 8, 3, 22, 45).time)
    internal val b19 = BusDepartureEntity(Date(118, 8, 3, 23, 48).time)
    internal val b20 = BusDepartureEntity(Date(118, 8, 4, 0, 45).time)

    internal val c1 = BusDepartureEntity(Date(118, 8, 1, 20, 20).time)
    internal val c2 = BusDepartureEntity(Date(118, 8, 1, 22, 30).time)
    internal val c3 = BusDepartureEntity(Date(118, 8, 1, 23, 30).time)

    internal val c4 = BusDepartureEntity(Date(118, 8, 2, 19, 48).time)
    internal val c5 = BusDepartureEntity(Date(118, 8, 2, 22, 30).time)
    internal val c6 = BusDepartureEntity(Date(118, 8, 2, 23, 30).time)
    internal val c7 = BusDepartureEntity(Date(118, 8, 3, 0, 30).time)

    internal val c8 = BusDepartureEntity(Date(118, 8, 3, 19, 48).time)
    internal val c9 = BusDepartureEntity(Date(118, 8, 3, 20, 48).time)
    internal val c10 = BusDepartureEntity(Date(118, 8, 3, 21, 48).time)
    internal val c11 = BusDepartureEntity(Date(118, 8, 3, 22, 48).time)
    internal val c12 = BusDepartureEntity(Date(118, 8, 3, 23, 30).time)
    internal val c13 = BusDepartureEntity(Date(118, 8, 4, 0, 30).time)

    internal val d1 = BusDepartureEntity(Date(118, 8, 1, 19, 48).time)
    internal val d2 = BusDepartureEntity(Date(118, 8, 1, 20, 48).time)
    internal val d3 = BusDepartureEntity(Date(118, 8, 1, 21, 48).time)
    internal val d4 = BusDepartureEntity(Date(118, 8, 1, 22, 48).time)
    internal val d5 = BusDepartureEntity(Date(118, 8, 1, 23, 10).time)
    internal val d6 = BusDepartureEntity(Date(118, 8, 1, 23, 42).time)
    internal val d7 = BusDepartureEntity(Date(118, 8, 2, 0, 10).time)
    internal val d8 = BusDepartureEntity(Date(118, 8, 2, 0, 12).time)

    internal val d9 = BusDepartureEntity(Date(118, 8, 2, 19, 48).time)
    internal val d10 = BusDepartureEntity(Date(118, 8, 2, 20, 20).time)
    internal val d11 = BusDepartureEntity(Date(118, 8, 2, 21, 18).time)
    internal val d12 = BusDepartureEntity(Date(118, 8, 2, 22, 18).time)
    internal val d13 = BusDepartureEntity(Date(118, 8, 2, 23, 10).time)
    internal val d14 = BusDepartureEntity(Date(118, 8, 3, 0, 10).time)

    internal val d15 = BusDepartureEntity(Date(118, 8, 3, 19, 33).time)
    internal val d16 = BusDepartureEntity(Date(118, 8, 3, 19, 36).time)
    internal val d17 = BusDepartureEntity(Date(118, 8, 3, 19, 48).time)
    internal val d18 = BusDepartureEntity(Date(118, 8, 3, 20, 18).time)
    internal val d19 = BusDepartureEntity(Date(118, 8, 3, 20, 36).time)
    internal val d20 = BusDepartureEntity(Date(118, 8, 3, 21, 3).time)
    internal val d21 = BusDepartureEntity(Date(118, 8, 3, 21, 36).time)
    internal val d22 = BusDepartureEntity(Date(118, 8, 3, 21, 48).time)
    internal val d23 = BusDepartureEntity(Date(118, 8, 3, 22, 6).time)
    internal val d24 = BusDepartureEntity(Date(118, 8, 3, 22, 27).time)
    internal val d25 = BusDepartureEntity(Date(118, 8, 3, 23, 5).time)
    internal val d26 = BusDepartureEntity(Date(118, 8, 4, 0, 10).time)

    init {
        prefill()
    }

    fun prefill() {
        box.removeAll()
        val amorbachTimes: Array<BusDepartureEntity> = arrayOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29)
        val dahenfeldTimes: Array<BusDepartureEntity> = arrayOf(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20)
        val neubergTimes: Array<BusDepartureEntity> = arrayOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13)
        val obereisesheimTimes: Array<BusDepartureEntity> = arrayOf(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26)
        amorbachTimes.forEach { it.busline.target = busLineRepository.amorbach }
        dahenfeldTimes.forEach { it.busline.target = busLineRepository.dahenfeld }
        neubergTimes.forEach { it.busline.target = busLineRepository.neuberg }
        obereisesheimTimes.forEach { it.busline.target = busLineRepository.obereisesheim }
        box.put(
                *amorbachTimes, *dahenfeldTimes, *neubergTimes, *obereisesheimTimes
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
