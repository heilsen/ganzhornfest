package de.heilsen.ganzhornfest.poi

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.database.Poi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PoiRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {
    fun getAll(): Flow<List<Poi>> {
        return ganzhornfestDb.poiQueries.selectClubs().asFlow().mapToList(Dispatchers.IO)
    }

    fun selectByName(name: String): Flow<List<Poi>> {
        return ganzhornfestDb.poiQueries.selectClubByName(name).asFlow().mapToList(Dispatchers.IO)
    }

    fun getStages(): Flow<List<String>> = ganzhornfestDb
        .poiQueries
        .selectStages(mapper = { _, name, _ -> name })
        .asFlow()
        .mapToList(Dispatchers.IO)
}

