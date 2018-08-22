package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Club

object ClubConverter {
    fun from(clubEntity: ClubEntity): Club {
        if (clubEntity == null) {
            return Club("club", "club")
        }
        return Club(clubEntity.name,
                clubEntity.description,
                FoodConverter.from(clubEntity.foods),
                emptyList(),
                emptyList())
    }
}