package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.ActionableOffer

object ActionableOfferConverter {
    fun from(actionableOfferEntity: ActionableOfferEntity): ActionableOffer {
        return ActionableOffer(actionableOfferEntity.name, actionableOfferEntity.description)
    }
}
