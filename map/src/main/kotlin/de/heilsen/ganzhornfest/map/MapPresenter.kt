package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
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
            val markerModel by remember {
                getMarkers()
                    .map<PersistentSet<MarkerUi>, MapModel> { MapModel.Data(markers = it) }
                    .catch {
                        Timber.e(it, "Failed to load map markers")
                        emit(MapModel.Error)
                    }
            }.collectAsState(initial = null)
            // The pin editor is a debug tool. A failed pin query empties the editor list,
            // it does not take the map down with it.
            val pins by remember {
                getClubPins().catch {
                    Timber.e(it, "Failed to load pin editor list")
                    emit(persistentListOf())
                }
            }.collectAsState(initial = persistentListOf())

            return when (val model = markerModel) {
                null -> MapModel.Loading()
                is MapModel.Data -> model.copy(pins = pins)
                else -> model
            }
        }
    }
