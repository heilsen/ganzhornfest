package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.GeoLocation
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class ClubEntity @JvmOverloads constructor(val name: String = "",
                                                val description: String = "") {
    @Id
    var id: Long = 0

    lateinit var foods: ToMany<FoodEntity>
    lateinit var drinks: ToMany<DrinkEntity>
    lateinit var actionableOffers: ToMany<ActionableOfferEntity>
    lateinit var geoLocation: ToMany<GeoLocationEntity>

}
