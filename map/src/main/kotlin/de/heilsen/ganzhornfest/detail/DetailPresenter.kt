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

            return when (val detailEvent = event) {
                is DetailEvent.Club -> {
                    val model by getClubDetail(detailEvent.clubName).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailEvent.Offer -> {
                    val model by getOfferDetail(detailEvent.offerName).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailEvent.Poi -> {
                    val model by getPoiDetail(detailEvent.name).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                is DetailEvent.PoiCategory -> {
                    val model by getPoiCategoryDetail(detailEvent.typeName).collectAsState(initial = null)
                    model ?: DetailModel.Loading
                }

                DetailEvent.Init -> DetailModel.Loading
            }
        }
    }
