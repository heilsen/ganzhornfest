package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetOfferDetailUseCase
    @Inject
    constructor(
        private val clubRepository: ClubRepository,
        private val offerRepository: OfferRepository,
    ) {
        operator fun invoke(offerId: Long): Flow<DetailModel.Success> =
            combine(
                offerRepository.getOfferName(offerId),
                clubRepository.getClubsByOffer(offerId),
            ) { name, clubs ->
                DetailModel.Success(
                    title = name.orEmpty(),
                    target = DetailTarget.Offer(offerId),
                    items =
                        clubs
                            .map { DetailItem(it.name, target = DetailTarget.Club(it.poiId)) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
