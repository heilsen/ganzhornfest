package de.heilsen.ganzhornfest.countdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.heilsen.ganzhornfest.core.FestivalEdition
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CountdownPresenter
    @Inject
    constructor(
        private val countdownUseCase: CountdownUseCase,
    ) {
        @Composable
        fun present(events: Flow<Unit>): CountdownModel {
            val timeZone = remember { TimeZone.of("Europe/Berlin") }
            val festivalStart = remember { FestivalEdition.days.first().atStartOfDayIn(timeZone) }
            val festivalEnd =
                remember {
                    FestivalEdition.days
                        .last()
                        .plus(1, DateTimeUnit.DAY)
                        .atStartOfDayIn(timeZone)
                }

            var model by remember {
                mutableStateOf(countdownUseCase(festivalStart, festivalEnd, Clock.System.now()))
            }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(1_000)
                    model = countdownUseCase(festivalStart, festivalEnd, Clock.System.now())
                }
            }

            return model
        }
    }
