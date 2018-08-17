package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer
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
        return ActionableOffer("action!") // ActionableOfferEntityDelegate(box.query().equal(OfferEntity_.name, name).build().findUnique()!!)
    }


    fun prefill() {
        box.removeAll()
        box.put(
                schnappfalle
        )
    }

}
