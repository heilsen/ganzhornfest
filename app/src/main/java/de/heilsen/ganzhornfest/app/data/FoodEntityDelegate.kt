package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Food


data class FoodEntityDelegate(private val foodEntity: FoodEntity) : Food(foodEntity.name, foodEntity.description)