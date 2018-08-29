package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Club
import de.heilsen.ganzhornfest.domain.entity.GeoLocation

object ClubConverter {
    fun from(clubEntity: ClubEntity): Club {
        return Club(clubEntity.name,
                clubEntity.description,
                FoodConverter.from(clubEntity.foods),
                DrinkConverter.from(clubEntity.drinks),
                ActionableOfferConverter.from(clubEntity.actionableOffers),
                GeolocationConverter.from(clubEntity.geoLocation))
    }
}