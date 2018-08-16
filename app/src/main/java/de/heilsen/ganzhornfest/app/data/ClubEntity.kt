package de.heilsen.ganzhornfest.app.data

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

}
