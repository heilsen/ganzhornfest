package de.heilsen.ganzhornfest.poi

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.Coordinate
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoordinateRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {

    fun getCoordinateByClubName(clubName: String): Flow<List<Pair<String, Coordinate>>> = ganzhornfestDb
        .poiCoordinateQueries
        .selectByClubName(clubName, mapper = { name, lat, lng -> name to Coordinate(1, lat, lng) })
        .asFlow()
        .mapToList(Dispatchers.IO)
}