package de.heilsen.ganzhornfest.program

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Date Time is stored as Zulu time (Z = +T00:00) in our SQLite DB.
 */
class ProgramRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {
    fun getPrograms(
        destination: String,
        startIsoDateTime: String,
        endIsoDateTime: String
    ): Flow<List<Program>> = ganzhornfestDb
        .programQueries
        .getPrograms(
            destination,
            startIsoDateTime,
            endIsoDateTime,
            ::toProgram
        )
        .asFlow()
        .mapToList(Dispatchers.IO)

    private fun toProgram(
        startUnixEpoch: String,
        endUnixEpoch: String?,
        name: String,
        description: String?,
        stage: String
    ): Program = Program(
        start = Instant.parse(startUnixEpoch).toLocalDateTime(TimeZone.currentSystemDefault()),
        end = endUnixEpoch?.let { Instant.parse(endUnixEpoch).toLocalDateTime(TimeZone.currentSystemDefault()) },
        name = name,
        description = description,
        stage = stage
    )
}
