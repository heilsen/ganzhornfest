package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.core.germanAlphaComparator
import de.heilsen.ganzhornfest.map.GetMarkersUseCase
import de.heilsen.ganzhornfest.map.MarkerUiType
import de.heilsen.ganzhornfest.map.germanLabel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoiCategoryDetailUseCase
    @Inject
    constructor(
        private val getMarkers: GetMarkersUseCase,
    ) {
        operator fun invoke(type: MarkerUiType): Flow<DetailModel.Success> =
            getMarkers().map { markers ->
                DetailModel.Success(
                    title = type.germanLabel(),
                    target = DetailTarget.Category(type),
                    items =
                        markers
                            .filter { it.markerUiType == type }
                            .distinctBy { it.poiId }
                            .map { DetailItem(it.title, target = DetailTarget.Poi(it.poiId)) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
