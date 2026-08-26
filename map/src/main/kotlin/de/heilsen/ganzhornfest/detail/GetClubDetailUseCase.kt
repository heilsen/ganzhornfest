package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetClubDetailUseCase
    @Inject
    constructor(
        private val clubRepository: ClubRepository,
    ) {
        operator fun invoke(clubName: String): Flow<DetailModel.Success> =
            clubRepository.getOffersByClub(clubName).map { offers ->
                DetailModel.Success(
                    title = clubName,
                    type = DetailType.Club,
                    items =
                        offers
                            .map { (name, description) -> DetailItem(name, description) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
