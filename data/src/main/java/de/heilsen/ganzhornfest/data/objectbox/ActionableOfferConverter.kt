package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer

object ActionableOfferConverter {
    fun from(actionableOfferEntity: ActionableOfferEntity): ActionableOffer {
        return ActionableOffer(actionableOfferEntity.name, actionableOfferEntity.description)
    }

    fun from(action: List<ActionableOfferEntity>): List<ActionableOffer> {
        return action.map { actionableOfferEntity: ActionableOfferEntity -> from(actionableOfferEntity) }
    }
}
