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
        operator fun invoke(poiId: Long): Flow<DetailModel.Success> =
            getMarkers().map { markers ->
                val marker = markers.firstOrNull { it.poiId == poiId }
                DetailModel.Success(
                    title = marker?.title.orEmpty(),
                    target = DetailTarget.Poi(poiId),
                    items =
                        listOfNotNull(
                            marker?.markerUiType?.let {
                                DetailItem(name = it.germanLabel(), target = DetailTarget.Category(it))
                            },
                        ),
                )
            }
    }
