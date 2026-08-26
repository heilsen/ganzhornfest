@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class FestivalInstantSerializerTest :
    DescribeSpec({
        it("parses a year-less festival time with year and timezone") {
            val serializer = FestivalInstantSerializer(year = 2026, timezone = "+02:00")
            val instant = Json.decodeFromString(serializer, "\"09-05T16:00\"")
            instant shouldBe Instant.parse("2026-09-05T16:00:00+02:00")
        }
    })
