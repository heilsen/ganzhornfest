package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Club

object ClubConverter {
    fun from(clubEntity: ClubEntity): Club {
        return Club(clubEntity.name,
                clubEntity.description,
                FoodConverter.from(clubEntity.foods),
                emptyList(),
                emptyList())
    }
}