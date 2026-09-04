package de.heilsen.ganzhornfest.navigation

import de.heilsen.ganzhornfest.detail.DetailTarget
import de.heilsen.ganzhornfest.detail.DetailType

// The one place a DetailTarget from the :map feature and an :app navigation route meet.
fun DetailTarget.toDestination(): Destination =
    when (this) {
        is DetailTarget.Club -> Destination.Detail(poiId, DetailType.Club)
        is DetailTarget.Offer -> Destination.Detail(offerId, DetailType.Offer)
        is DetailTarget.Poi -> Destination.Detail(poiId, DetailType.Poi)
        is DetailTarget.Category -> Destination.CategoryDetail(type)
    }

fun Destination.Detail.toTarget(): DetailTarget =
    when (type) {
        DetailType.Club -> DetailTarget.Club(id)
        DetailType.Offer -> DetailTarget.Offer(id)
        DetailType.Poi -> DetailTarget.Poi(id)
    }

fun Destination.CategoryDetail.toTarget(): DetailTarget = DetailTarget.Category(category)
