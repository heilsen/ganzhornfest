package de.heilsen.ganzhornfest.core.datetime

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import java.util.Locale

class LocalDateFormatterTest :
    DescribeSpec({
        describe("formatToLocalWeekdayDate") {
            it("keeps German day-dot-month order regardless of the JVM default locale") {
                val previousDefault = Locale.getDefault()
                Locale.setDefault(Locale.US)
                try {
                    formatToLocalWeekdayDate(LocalDate(2026, 9, 5)) shouldBe "Sa., 05.09."
                } finally {
                    Locale.setDefault(previousDefault)
                }
            }
        }
    })
