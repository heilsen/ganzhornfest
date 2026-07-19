package de.heilsen.ganzhornfest.bus

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@OptIn(kotlin.time.ExperimentalTime::class)
interface GetBusConnectionsUseCase {
    operator fun invoke(
        destination: String,
        start: Instant, // Instant is best for getting dates in and out of the DB.
        end: Instant, // Instant is best for getting dates in and out of the DB.
    ): Flow<PersistentList<BusConnection>>
}
