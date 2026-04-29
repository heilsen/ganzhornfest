package de.heilsen.ganzhornfest.countdown

import dev.zacsweers.metro.Inject
import kotlinx.datetime.Instant

@OptIn(kotlin.time.ExperimentalTime::class)
class CountdownUseCase @Inject constructor() {
    operator fun invoke(
        festivalStart: Instant,
        festivalEnd: Instant,
        now: Instant,
    ): CountdownModel = when {
        now >= festivalEnd -> CountdownModel.After
        now >= festivalStart -> CountdownModel.During
        else -> {
            val totalSeconds = (festivalStart - now).inWholeSeconds
            CountdownModel.Before(
                days = totalSeconds / 86400,
                hours = (totalSeconds % 86400) / 3600,
                minutes = (totalSeconds % 3600) / 60,
                seconds = totalSeconds % 60,
            )
        }
    }
}
