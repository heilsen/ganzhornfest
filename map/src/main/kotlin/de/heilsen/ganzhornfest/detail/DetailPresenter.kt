package de.heilsen.ganzhornfest.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.android.gms.maps.model.LatLng
import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.drink.data.DrinkRepository
import de.heilsen.ganzhornfest.food.data.FoodRepository
import de.heilsen.ganzhornfest.map.MapModel
import de.heilsen.ganzhornfest.map.MarkerUi
import de.heilsen.ganzhornfest.map.MarkerUiType
import de.heilsen.ganzhornfest.poi.CoordinateRepository
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val getClubDetail: GetClubDetailUseCase,
    private val getOfferDetail: GetOfferDetailUseCase,
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
                val model by getOfferDetail(detailEvent.offerMame).collectAsState(initial = null)
                model ?: DetailModel.Loading
            }

            DetailEvent.Init -> DetailModel.Loading
        }
    }
}

class GetClubDetailUseCase @Inject constructor(
    private val clubRepository: ClubRepository,
    private val coordinateRepository: CoordinateRepository,
) {
    operator fun invoke(clubName: String): Flow<DetailModel.Club> {
        val offerList = clubRepository.getOffersByClub(clubName)
        val coordinate = coordinateRepository.getCoordinateByClubName(clubName).map {
            it.map { (clubName, coordinate) ->
                MarkerUi(clubName, LatLng(coordinate.lat, coordinate.lng), MarkerUiType.CLUB)
            }.first()
        }
        return combine(offerList, coordinate) { offers, marker ->
            DetailModel.Club(
                clubName,
                offers,
                mapModel = MapModel.Data(
                    persistentSetOf(marker)
                )
            )

        }
    }
}

class GetOfferDetailUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    private val drinkRepository: DrinkRepository
) {
    operator fun invoke(offerName: String): Flow<DetailModel.Offer> {
        //TODO: implement me
//        DetailModel.Offer(
//            offerName,
//            clubList,
//            mapModel = MapModel.Data(
//                clubMarkers
//            )
//        )
        TODO("implement me")
    }
}