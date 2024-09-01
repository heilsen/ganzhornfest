package de.heilsen.ganzhornfest.map

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.google.android.gms.maps.model.LatLng
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClubCoordinatesRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {
    fun getPoiCoordinates(): Flow<List<MarkerDTO>> = ganzhornfestDb
        .poiCoordinateQueries
        .selectAll(mapper = { name, type, lat, lng -> MarkerDTO(name, type, LatLng(lat, lng)) })
        .asFlow()
        .mapToList(Dispatchers.IO)

    fun getClubCoordinates(clubName: String): Flow<List<MarkerDTO>> = ganzhornfestDb
        .poiCoordinateQueries
        .selectByClubName(clubName, mapper = { name, lat, lng -> MarkerDTO(name, "club", LatLng(lat, lng)) })
        .asFlow()
        .mapToList(Dispatchers.IO)
}


data class MarkerDTO(
    val name: String,
    val type: String,
    val latLng: LatLng
)