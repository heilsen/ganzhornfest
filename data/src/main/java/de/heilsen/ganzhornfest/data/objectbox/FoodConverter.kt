package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Food

object FoodConverter {
    fun from(foodEntity: FoodEntity): Food {
        return Food(foodEntity.name, foodEntity.description)
    }

    fun from(foodEntityList: List<FoodEntity>): List<Food> {
        return foodEntityList.map { foodEntity: FoodEntity -> from(foodEntity) }
    }
}