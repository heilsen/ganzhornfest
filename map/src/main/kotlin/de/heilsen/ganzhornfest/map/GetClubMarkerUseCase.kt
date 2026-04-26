package de.heilsen.ganzhornfest.map

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.zacsweers.metro.Inject

class GetClubMarkerUseCase @Inject constructor(private val clubCoordinatesRepository: ClubCoordinatesRepository) {
    operator fun invoke(clubName: String): Flow<PersistentSet<MarkerUi>> {
        return clubCoordinatesRepository
            .getClubCoordinates(clubName)
            .map { markerList ->
                markerList
                    .map { (name, _, latLng) ->
                        MarkerUi(
                            title = name,
                            latLng = latLng,
                            markerUiType = MarkerUiType.CLUB,
                        )
                    }.toPersistentSet()
            }
    }
}