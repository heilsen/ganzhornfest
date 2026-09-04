package de.heilsen.ganzhornfest.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

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
                is DetailTarget.Club -> {
                    val model by getClubDetail(target.poiId).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailTarget.Offer -> {
                    val model by getOfferDetail(target.offerId).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailTarget.Poi -> {
                    val model by getPoiDetail(target.poiId).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailTarget.Category -> {
                    val model by getPoiCategoryDetail(target.type).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }
            }
        }
    }
