package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Club


data class ClubEntityDelegate(private val clubEntity: ClubEntity) : Club(
        clubEntity.name,
        clubEntity.description,
        clubEntity.abbreviation,
        clubEntity.foods.map { FoodEntityDelegate(it) },
        emptyList(),
        emptyList())
