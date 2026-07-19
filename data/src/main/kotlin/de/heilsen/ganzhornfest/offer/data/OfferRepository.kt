package de.heilsen.ganzhornfest.offer.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.database.Offer
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class OfferRepository
    @Inject
    constructor(
        private val ganzhornfestDb: GanzhornfestDb,
    ) {
        fun getAllFood(): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectAllFood()
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun selectFoodByName(name: String): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectFoodByName(name)
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun getAllDrinks(): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectAllDrinks()
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun selectDrinkByName(name: String): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectDrinkByName(name)
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun getAll(): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectAll()
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun selectByName(name: String): Flow<List<Offer>> =
            ganzhornfestDb.offerQueries
                .selectByName(name)
                .asFlow()
                .mapToList(Dispatchers.IO)
    }
