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
import javax.inject.Inject

class GetClubDetailUseCase @Inject constructor(
    private val clubRepository: ClubRepository,
    private val clubCoordinatesRepository: ClubCoordinatesRepository,
) {
    operator fun invoke(clubName: String): Flow<DetailModel.Success> {
        val offerList = clubRepository.getOffersByClub(clubName)
        //TODO: verify that given clubName exists
        val coordinates: Flow<List<MarkerUi>> =
            clubCoordinatesRepository.getClubCoordinates(clubName).map {
                it.map { (clubName, type /* must be "club" */, latLng) ->
                    MarkerUi(clubName, latLng, MarkerUiType.CLUB)
                }
            }
        return combine(offerList, coordinates) { offers, markers ->
            DetailModel.Success(
                title = clubName, type = DetailType.Club, mapModel = MapModel.Data(
                    markers.toImmutableSet(),
                    isFullscreen = false,
                    showLegend = false,
                    showWindowInfo = true
                ), items = offers
            )
        }
    }
}