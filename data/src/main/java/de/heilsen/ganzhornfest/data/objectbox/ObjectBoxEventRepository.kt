package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Event
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore
import java.util.*

class ObjectBoxEventRepository(boxStore: BoxStore) : Repository<Event> {

    internal var box: Box<EventEntity> = boxStore.boxFor(EventEntity::class.java)

    internal val eroeffnung = EventEntity("Eröffnung durch Oberbürgermeister Steffen Hertwig und der ehemaligen Württemberger Weinkönigin Andrea Ritz",
            Date(118, 8, 1, 16, 0).time)
    internal val mvObereisesheim = EventEntity("MV Obereisesheim",
            Date(118, 8, 1, 16, 0).time)
    internal val tanzgruppe = EventEntity("Tanzgruppe \"Hotsteppers\" (Neckarsulmer Sportunion)",
            Date(118, 8, 1, 18, 40).time)
    internal val mgvDahenfeld = EventEntity("MGV Dahenfeld",
            Date(118, 8, 1, 20, 0).time)
    internal val someEventOnSecondDay = EventEntity("irgendein Event",
            Date(118, 8, 2, 20, 0).time)
    internal val someEventOnThirdDay = EventEntity("irgendein Event",
            Date(118, 8, 3, 20, 0).time)

    init {
        prefill()
    }

    override fun getAll(): List<Event> {
        return box.all.map { EventConverter.from(it) }
    }


    override fun get(i: Int): Event {
        return EventConverter.from(box.get(i.toLong()))
    }

    override fun get(description: String): Event { //TODO: split Repository into those who can be search by names and those who can't
        val entity = box.query().equal(EventEntity_.description, description).build().findUnique() ?: throw EntityNotFoundException()
        return EventConverter.from(entity)
    }


    fun prefill() {
        box.removeAll()
        box.put(
                eroeffnung,
                mvObereisesheim,
                tanzgruppe,
                mgvDahenfeld,
                someEventOnSecondDay,
                someEventOnThirdDay
        )
    }

}
