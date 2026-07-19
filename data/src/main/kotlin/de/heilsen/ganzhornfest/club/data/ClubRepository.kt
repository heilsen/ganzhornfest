package de.heilsen.ganzhornfest.club.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ClubRepository
    @Inject
    constructor(
        private val ganzhornfestDb: GanzhornfestDb,
    ) {
        fun getOffersByClub(clubName: String): Flow<List<String>> =
            ganzhornfestDb.clubOfferQueries
                .selectOffersByClubName(clubName)
                .asFlow()
                .mapToList(Dispatchers.IO)

        fun getClubsByOffer(offerName: String): Flow<List<String>> =
            ganzhornfestDb.clubOfferQueries
                .selectClubsByOfferName(offerName)
                .asFlow()
                .mapToList(Dispatchers.IO)
    }
