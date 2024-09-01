package de.heilsen.ganzhornfest.program

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import javax.inject.Inject

class GetProgramsUseCase @Inject constructor(private val programRepository: ProgramRepository){
    operator fun invoke(
        location: String?,
        start: Instant,
        end: Instant
    ): Flow<ImmutableList<Program>> {
        if (location == null) return flowOf(persistentListOf())
        return programRepository
            .getPrograms(location, start.toString(), end.toString())
            .map { it.toPersistentList() }
    }
}
