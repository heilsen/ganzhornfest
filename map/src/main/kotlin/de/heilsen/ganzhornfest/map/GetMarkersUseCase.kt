package de.heilsen.ganzhornfest.map

import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMarkersUseCase
    @Inject
    constructor(
        private val poiCoordinatesRepository: ClubCoordinatesRepository,
    ) {
        operator fun invoke(): Flow<PersistentSet<MarkerUi>> =
            poiCoordinatesRepository
                .getPoiCoordinates()
                .map { markerList ->
                    markerList
                        .map { (name, type, latLng) ->
                            MarkerUi(
                                title = name,
                                markerUiType =
                                    when (type) {
                                        "club" -> MarkerUiType.CLUB
                                        "event location" -> MarkerUiType.EVENT_LOCATION
                                        "playground" -> MarkerUiType.PLAYGROUND
                                        "wc" -> MarkerUiType.WC
                                        "first aid" -> MarkerUiType.FIRST_AID
                                        "busstop" -> MarkerUiType.BUS_STOP
                                        else -> error("markerUiType='$type' is not a known marker type")
                                    },
                                latLng = latLng,
                            )
                        }.toPersistentSet()
                }
    }
