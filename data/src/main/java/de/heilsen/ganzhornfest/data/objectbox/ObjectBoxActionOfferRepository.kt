package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxActionOfferRepository(boxStore: BoxStore) : Repository<ActionableOffer> {

    internal var box: Box<ActionableOfferEntity> = boxStore.boxFor(ActionableOfferEntity::class.java)

    internal val schnappfalle = ActionableOfferEntity("Schnappfalle")

    init {
        prefill()
    }

    override fun getAll(): List<ActionableOffer> {
        return box.all.map { ActionableOfferConverter.from(it) }
    }


    override fun get(i: Int): ActionableOffer {
        return ActionableOfferConverter.from(box.get(i.toLong()))
    }

    override fun get(name: String): ActionableOffer {
        val entity = box.query().equal(ActionableOfferEntity_.name, name).build().findUnique() ?: throw EntityNotFoundException()
        return ActionableOfferConverter.from(entity)
    }


    fun prefill() {
        box.removeAll()
        box.put(
                schnappfalle
        )
    }

}
