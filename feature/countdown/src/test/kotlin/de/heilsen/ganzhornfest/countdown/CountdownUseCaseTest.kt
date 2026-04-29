@file:OptIn(kotlin.time.ExperimentalTime::class)

package de.heilsen.ganzhornfest.countdown

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Instant
import kotlin.time.Duration.Companion.seconds

class CountdownUseCaseTest : DescribeSpec({
    val useCase = CountdownUseCase()

    // Festival: Sept 5–7 2026, Europe/Berlin (UTC+2 in summer)
    val festivalStart = Instant.parse("2026-09-05T00:00:00+02:00")
    val festivalEnd = Instant.parse("2026-09-08T00:00:00+02:00")

    describe("CountdownUseCase") {
        it("returns Before with correct hours when 2 hours remain") {
            val now = festivalStart - 7200.seconds
            useCase(festivalStart, festivalEnd, now) shouldBe
                CountdownModel.Before(days = 0, hours = 2, minutes = 0, seconds = 0)
        }

        it("returns Before with 1 second remaining") {
            val now = festivalStart - 1.seconds
            useCase(festivalStart, festivalEnd, now) shouldBe
                CountdownModel.Before(days = 0, hours = 0, minutes = 0, seconds = 1)
        }

        it("decomposes 90 seconds into 1 minute and 30 seconds") {
            val now = festivalStart - 90.seconds
            useCase(festivalStart, festivalEnd, now) shouldBe
                CountdownModel.Before(days = 0, hours = 0, minutes = 1, seconds = 30)
        }

        it("decomposes days, hours, minutes, seconds correctly") {
            val now = festivalStart - (1 * 86400 + 2 * 3600 + 3 * 60 + 4).seconds
            useCase(festivalStart, festivalEnd, now) shouldBe
                CountdownModel.Before(days = 1, hours = 2, minutes = 3, seconds = 4)
        }

        it("returns During when now equals festival start") {
            useCase(festivalStart, festivalEnd, festivalStart) shouldBe CountdownModel.During
        }

        it("returns During when now is mid-festival") {
            val now = Instant.parse("2026-09-06T12:00:00+02:00")
            useCase(festivalStart, festivalEnd, now) shouldBe CountdownModel.During
        }

        it("returns After when now equals festival end") {
            useCase(festivalStart, festivalEnd, festivalEnd) shouldBe CountdownModel.After
        }

        it("returns After when now is past festival end") {
            val now = Instant.parse("2026-10-01T12:00:00+02:00")
            useCase(festivalStart, festivalEnd, now) shouldBe CountdownModel.After
        }
    }
})
