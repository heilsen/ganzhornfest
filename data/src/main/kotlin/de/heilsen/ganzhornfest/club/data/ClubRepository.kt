package de.heilsen.ganzhornfest.club.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.ClubOffer
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.database.Poi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClubRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {

    fun getOffersByClub(clubName: String): Flow<List<String>> {
        return ganzhornfestDb.clubOfferQueries.selectOffersByClubName(clubName).asFlow().mapToList(Dispatchers.IO)
    }
}