package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Busline
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore


class ObjectBoxBusLineRepository(boxStore: BoxStore) : Repository<Busline> {
    internal var box: Box<BuslineEntity> = boxStore.boxFor(BuslineEntity::class.java)
    internal val amorbach = BuslineEntity("Amorbach", "Amorbach (Linie 624/691/Sonderbus)")
    internal val dahenfeld = BuslineEntity("Dahenfeld", "Dahenfeld (Linie 624/N20/Sonderbus)")
    internal val neuberg = BuslineEntity("Neuberg", "Neuberg (Linie 92/Sonderbus)")
    internal val obereisesheim = BuslineEntity("Obereisesheim", "Obereisesheim (Linie 694/695/94/Sonderbus)")

    init {
        prefill()
    }

    fun prefill() {
        box.removeAll()
        box.put(
                amorbach,dahenfeld,neuberg,obereisesheim
        )
    }

    override fun getAll(): List<Busline> {
        return box.all.map { BuslineConverter.from(it) }
    }

    override fun get(i: Int): Busline {
        return BuslineConverter.from(box.get(i.toLong()))
    }

    @Throws(EntityNotFoundException::class)
    override fun get(name: String): Busline {
        throw EntityNotFoundException()
    }
}
