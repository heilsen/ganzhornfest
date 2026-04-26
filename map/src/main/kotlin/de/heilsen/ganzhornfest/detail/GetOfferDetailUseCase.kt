package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.map.ClubCoordinatesRepository
import de.heilsen.ganzhornfest.map.MapModel
import de.heilsen.ganzhornfest.map.MarkerUi
import de.heilsen.ganzhornfest.map.MarkerUiType
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import dev.zacsweers.metro.Inject

class GetOfferDetailUseCase @Inject constructor(
    private val clubCoordinatesRepository: ClubCoordinatesRepository,
    private val clubRepository: ClubRepository
) {
    operator fun invoke(offerName: String): Flow<DetailModel.Success> {
        //TODO: verify that given offerName exists
        val clubsFlow = clubRepository.getClubsByOffer(offerName)
        val markerUiFlow = clubCoordinatesRepository.getClubCoordinatesByOffer(offerName).map {
            val asdf = it.map { (clubName, type /* assuming always "club" */, latLng) ->
                MarkerUi(clubName, latLng, MarkerUiType.CLUB)
            }
            return@map asdf
        }

        return combine(clubsFlow, markerUiFlow) { clubs, markerUiList ->
            DetailModel.Success(
                title = offerName,
                type = DetailType.Offer,
                mapModel = MapModel.Data(
                    markerUiList.toImmutableSet(),
                    isFullscreen = false,
                    showLegend = false,
                    showWindowInfo = true
                ),
                items = clubs
            )
        }

    }
}