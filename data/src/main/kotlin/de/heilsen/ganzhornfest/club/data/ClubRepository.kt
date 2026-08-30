package de.heilsen.ganzhornfest.club.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

data class ClubOfferRow(
    val offerId: Long,
    val name: String,
    val description: String?,
)

data class ClubRow(
    val poiId: Long,
    val name: String,
)

class ClubRepository
    @Inject
    constructor(
        private val ganzhornfestDb: GanzhornfestDb,
    ) {
        fun getClubName(poiId: Long): Flow<String?> =
            ganzhornfestDb.poiQueries
                .selectNameById(poiId)
                .asFlow()
                .mapToOneOrNull(Dispatchers.IO)

        fun getOffersByClub(poiId: Long): Flow<List<ClubOfferRow>> =
            ganzhornfestDb.clubOfferQueries
                .selectOffersByPoiId(poiId, mapper = ::ClubOfferRow)
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun getClubsByOffer(offerId: Long): Flow<List<ClubRow>> =
            ganzhornfestDb.clubOfferQueries
                .selectClubsByOfferId(offerId, mapper = ::ClubRow)
                .asFlow()
                .mapToList(Dispatchers.IO)
    }
