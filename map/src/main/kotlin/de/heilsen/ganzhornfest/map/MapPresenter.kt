package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.catch
import timber.log.Timber

class MapPresenter
    @Inject
    constructor(
        private val getMarkers: GetMarkersUseCase,
        private val getClubPins: GetClubPinsUseCase,
        private val poiCoordinatesRepository: ClubCoordinatesRepository,
    ) {
        fun applyLatLng(
            coordinateId: Long,
            lat: Double,
            lng: Double,
        ) {
            poiCoordinatesRepository.updateCoordinate(coordinateId, lat, lng)
        }

        @Composable
        fun present(): MapModel {
            val markers by remember {
                getMarkers().catch {
                    Timber.e(it, "Failed to load map markers")
                    emit(persistentSetOf())
                }
            }.collectAsState(initial = persistentSetOf())
            val pins by remember {
                getClubPins().catch {
                    Timber.e(it, "Failed to load pin editor list")
                    emit(persistentListOf())
                }
            }.collectAsState(initial = persistentListOf())

            return MapModel.Data(
                markers = markers,
                pins = pins,
            )
        }
    }
