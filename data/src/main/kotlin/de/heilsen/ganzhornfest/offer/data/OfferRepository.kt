package de.heilsen.ganzhornfest.offer.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.database.Offer
import de.heilsen.ganzhornfest.database.OfferAlias
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

data class OfferSearchResult(
    val id: Long,
    val name: String,
    val description: String?,
    val clubs: String,
)

class OfferRepository
    @Inject
    constructor(
        private val ganzhornfestDb: GanzhornfestDb,
    ) {
        fun getAllFood(): Flow<List<OfferSearchResult>> =
            ganzhornfestDb.offerQueries
                .selectAllFood { id, name, description, clubs ->
                    OfferSearchResult(id, name, description, clubs ?: "")
                }.asFlow()
                .mapToList(Dispatchers.IO)

        fun getAllDrinks(): Flow<List<OfferSearchResult>> =
            ganzhornfestDb.offerQueries
                .selectAllDrinks { id, name, description, clubs ->
                    OfferSearchResult(id, name, description, clubs ?: "")
                }.asFlow()
                .mapToList(Dispatchers.IO)

        fun getOfferName(offerId: Long): Flow<String?> =
            ganzhornfestDb.offerQueries
                .selectNameById(offerId)
                .asFlow()
                .mapToOneOrNull(Dispatchers.IO)

        fun getAliases(): Flow<List<OfferAlias>> =
            ganzhornfestDb.offerAliasQueries
                .selectAll()
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
