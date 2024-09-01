package de.heilsen.ganzhornfest.program

import de.heilsen.ganzhornfest.poi.PoiRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStagesUseCase @Inject constructor(private val poiRepository: PoiRepository) {
    operator fun invoke(
    ): Flow<ImmutableList<String>> =
        poiRepository.getStages()
            .map { it.toPersistentList() }
}
