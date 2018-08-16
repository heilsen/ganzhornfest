package de.heilsen.ganzhornfest.app.presenter

import de.heilsen.ganzhornfest.domain.entity.Club
import de.heilsen.ganzhornfest.domain.entity.Offer

object ListableItemConverter {
    private fun fromClub(club: Club): ListableItem {
        return ListableItem(club.name, club.description)
    }

    fun fromClubList(clubList: List<Club>): List<ListableItem> {
        return clubList.map { club: Club -> fromClub(club) }
    }

    fun fromOfferList(offerList: List<Offer>): List<ListableItem> {
        return offerList.map { fromOffer(it) }
    }

    private fun fromOffer(offer: Offer): ListableItem {
        return ListableItem(offer.name, offer.description)
    }
}