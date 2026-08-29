package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.map.GetMarkersUseCase
import de.heilsen.ganzhornfest.map.germanLabel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoiDetailUseCase
    @Inject
    constructor(
        private val getMarkers: GetMarkersUseCase,
    ) {
        operator fun invoke(poiName: String): Flow<DetailModel.Success> =
            getMarkers().map { markers ->
                val type = markers.firstOrNull { it.title == poiName }?.markerUiType
                DetailModel.Success(
                    title = poiName,
                    type = DetailType.Poi,
                    items =
                        listOfNotNull(
                            type?.let { DetailItem(name = it.germanLabel(), routeKey = it.name) },
                        ),
                )
            }
    }
