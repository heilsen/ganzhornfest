package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Drink

object DrinkConverter {
    fun from(drinkEntity: DrinkEntity): Drink {
        return Drink(drinkEntity.name, drinkEntity.description)
    }
}