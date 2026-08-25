package de.heilsen.ganzhornfest.map

import com.google.android.gms.maps.model.LatLng
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetClubPinsUseCase
    @Inject
    constructor(
        private val poiCoordinatesRepository: ClubCoordinatesRepository,
    ) {
        operator fun invoke(): Flow<ImmutableList<ClubPin>> =
            poiCoordinatesRepository.getClubPins().map { rows ->
                rows
                    .map { row ->
                        ClubPin(
                            poiId = row.poiId,
                            coordinateId = row.coordinateId,
                            name = row.name,
                            chipLabel = chipLabel(row.poiId, row.name, row.coordinateId),
                            latLng =
                                if (row.lat != null && row.lng != null) {
                                    LatLng(row.lat, row.lng)
                                } else {
                                    null
                                },
                        )
                    }.sortedBy { it.chipLabel }
                    .toPersistentList()
            }
    }
