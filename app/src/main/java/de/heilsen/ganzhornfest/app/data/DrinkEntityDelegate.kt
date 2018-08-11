package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Drink

data class DrinkEntityDelegate(private val drinkEntity: DrinkEntity) : Drink(drinkEntity.name, drinkEntity.description)
