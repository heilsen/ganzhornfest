package de.heilsen.ganzhornfest.food.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.Offer
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {
    fun getAll(): Flow<List<Offer>> {
        return ganzhornfestDb.offerQueries.selectAllFood().asFlow().mapToList(Dispatchers.IO)
    }

    fun selectByName(name: String): Flow<List<Offer>> {
        return ganzhornfestDb.offerQueries.selectFoodByName(name).asFlow().mapToList(Dispatchers.IO)
    }
}