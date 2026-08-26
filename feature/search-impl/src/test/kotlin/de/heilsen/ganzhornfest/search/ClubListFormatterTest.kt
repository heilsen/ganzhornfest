package de.heilsen.ganzhornfest.search

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ClubListFormatterTest :
    DescribeSpec({
        val many = "mehrere Vereine"

        it("keeps a single club name") {
            formatClubList("ASB", many) shouldBe "ASB"
        }
        it("keeps two club names") {
            formatClubList("ASB,DLRG", many) shouldBe "ASB,DLRG"
        }
        it("replaces three or more names with the many-clubs label") {
            formatClubList("ASB,DLRG,SUN", many) shouldBe many
        }
        it("trims spaces when counting") {
            formatClubList("ASB, DLRG, SUN", many) shouldBe many
        }
        it("returns empty input unchanged") {
            formatClubList("", many) shouldBe ""
        }
    })
