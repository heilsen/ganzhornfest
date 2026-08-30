package de.heilsen.ganzhornfest.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber

class DetailPresenter
    @Inject
    constructor(
        private val getClubDetail: GetClubDetailUseCase,
        private val getOfferDetail: GetOfferDetailUseCase,
        private val getPoiDetail: GetPoiDetailUseCase,
        private val getPoiCategoryDetail: GetPoiCategoryDetailUseCase,
    ) {
        @Composable
        fun present(events: Flow<DetailEvent>): DetailModel {
            val event: DetailEvent by events.collectAsState(initial = DetailEvent.Init as DetailEvent)

            val open = event as? DetailEvent.Open ?: return DetailModel.Loading

            return when (val target = open.target) {
                is DetailTarget.Club -> rememberDetailModel(target) { getClubDetail(target.poiId) }
                is DetailTarget.Offer -> rememberDetailModel(target) { getOfferDetail(target.offerId) }
                is DetailTarget.Poi -> rememberDetailModel(target) { getPoiDetail(target.poiId) }
                is DetailTarget.Category ->
                    rememberDetailModel(target) { getPoiCategoryDetail(target.type) }
            }
        }
    }

// A failed query has to become a value. Thrown, it kills the flow, collectAsState never
// delivers, and the screen sits on Loading forever with nothing to show.
// remember keyed on the target keeps one subscription per detail. Without it every
// recomposition builds a fresh Flow and collectAsState restarts the query.
@Composable
private fun rememberDetailModel(
    key: DetailTarget,
    source: () -> Flow<DetailModel>,
): DetailModel {
    val model by remember(key) {
        source().catch {
            Timber.e(it, "Failed to load detail for %s", key)
            emit(DetailModel.Error)
        }
    }.collectAsState(initial = null)
    return model ?: DetailModel.Loading
}
