package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOfferDetailUseCase
    @Inject
    constructor(
        private val clubRepository: ClubRepository,
    ) {
        operator fun invoke(offerName: String): Flow<DetailModel.Success> =
            clubRepository.getClubsByOffer(offerName).map { clubs ->
                DetailModel.Success(
                    title = offerName,
                    type = DetailType.Offer,
                    items =
                        clubs
                            .map { DetailItem(it) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
