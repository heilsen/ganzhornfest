package de.heilsen.ganzhornfest.map

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMarkersUseCase @Inject constructor(private val poiCoordinatesRepository: ClubCoordinatesRepository) {
    operator fun invoke(): Flow<PersistentSet<MarkerUi>> {
        return poiCoordinatesRepository
            .getPoiCoordinates()
            .map { markerList ->
                markerList
                    .map { (name, type, latLng) ->
                        MarkerUi(
                            title = name,
                            markerUiType = when (type) {
                                "club" -> MarkerUiType.CLUB
                                "stage" -> MarkerUiType.STAGE
                                "playground" -> MarkerUiType.PLAYGROUND
                                "wc" -> MarkerUiType.WC
                                "first aid" -> MarkerUiType.FIRST_AID
                                "busstop" -> MarkerUiType.BUS_STOP
                                else -> error("$type is not a knonw marker type")
                            },
                            latLng = latLng
                        )
                    }.toPersistentSet()
            }
    }
}
