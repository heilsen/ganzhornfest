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
        operator fun invoke(typeName: String): Flow<DetailModel.Success> =
            getMarkers().map { markers ->
                val type = runCatching { MarkerUiType.valueOf(typeName) }.getOrNull()
                DetailModel.Success(
                    title = type?.germanLabel().orEmpty(),
                    type = DetailType.PoiCategory,
                    items =
                        markers
                            .filter { it.markerUiType == type }
                            .distinctBy { it.title }
                            .map { DetailItem(it.title) }
                            .sortedWith(compareBy(germanAlphaComparator(), DetailItem::name)),
                )
            }
    }
