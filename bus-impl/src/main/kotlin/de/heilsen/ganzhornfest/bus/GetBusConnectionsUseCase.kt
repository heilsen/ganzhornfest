package de.heilsen.ganzhornfest.bus

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Instant
import javax.inject.Inject

class GetBusConnectionsUseCaseImpl @Inject constructor(
    private val busConnectionRepository: BusConnectionRepository
) : GetBusConnectionsUseCase {
    @OptIn(kotlin.time.ExperimentalTime::class)
    override operator fun invoke(
        destination: String,
        start: Instant, //Instant is best for getting dates in and out of the DB.
        end: Instant //Instant is best for getting dates in and out of the DB.
    ): Flow<PersistentList<BusConnection>> = busConnectionRepository.getBusConnection(
        destination,
        start.toString(),
        end.toString()
    ).map { it.toPersistentList() }
}

