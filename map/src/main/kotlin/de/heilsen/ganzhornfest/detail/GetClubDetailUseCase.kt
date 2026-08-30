package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetClubDetailUseCase
    @Inject
    constructor(
        private val clubRepository: ClubRepository,
    ) {
        operator fun invoke(poiId: Long): Flow<DetailModel.Success> =
            combine(
                clubRepository.getClubName(poiId),
                clubRepository.getOffersByClub(poiId),
            ) { name, offers ->
                DetailModel.Success(
                    title = name.orEmpty(),
                    target = DetailTarget.Club(poiId),
                    items =
                        offers
                            .map { DetailItem(it.name, it.description, DetailTarget.Offer(it.offerId)) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
