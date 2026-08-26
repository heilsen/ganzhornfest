@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class FestivalDataParseTest :
    DescribeSpec({
        it("parses a 2026-shaped fixture including programs") {
            val stream =
                checkNotNull(
                    FestivalDataParseTest::class.java.classLoader.getResourceAsStream(
                        "festival/fixture-data.json",
                    ),
                )
            val data = stream.use { parseFestivalData(it, year = 2026, timezone = "+02:00") }

            data.poiTypes.map { it.name } shouldBe listOf("club", "event location", "attraction")
            data.pois.map { it.name } shouldBe
                listOf("Arbeiter-Samariter-Bund", "Hauptbühne Museumsplatz")
            data.programs.size shouldBe 2
            data.programs[0].name shouldBe "Eröffnung des Festes"
            data.programs[0].start shouldBe Instant.parse("2026-09-05T16:00:00+02:00")
            data.programs[0].end shouldBe null
            data.programs[1].end shouldBe Instant.parse("2026-09-05T18:00:00+02:00")
            data.busConnections[0].departure shouldBe Instant.parse("2026-09-07T19:18:00+02:00")
        }
    })
