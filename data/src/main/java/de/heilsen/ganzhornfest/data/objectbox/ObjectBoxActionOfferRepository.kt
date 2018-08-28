package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxActionOfferRepository(boxStore: BoxStore) : Repository<ActionableOffer> {

    internal var box: Box<ActionableOfferEntity> = boxStore.boxFor(ActionableOfferEntity::class.java)

    internal val schnappfalle = ActionableOfferEntity("Schnappfalle")
    internal val kinderschminken = ActionableOfferEntity("Kinderschminken")
    internal val dosenwerfen = ActionableOfferEntity("Dosenwerfen")
    internal val raupenbasteln = ActionableOfferEntity("Raupen basteln")
    internal val ponyreiten = ActionableOfferEntity("Ponyreiten")
    internal val kinderspiele = ActionableOfferEntity("Kinderspiele")
    internal val musikprogramm = ActionableOfferEntity("Musikprogramm (siehe Programm)")
    internal val wurfwand = ActionableOfferEntity("Wurfwand")
    internal val schiessbude = ActionableOfferEntity("Schießbudenstand")

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
                schnappfalle,
                kinderschminken,dosenwerfen,raupenbasteln,ponyreiten,kinderspiele,musikprogramm,wurfwand,schiessbude
        )
    }

}
