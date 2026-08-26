package de.heilsen.ganzhornfest.search

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GermanQueryNormalizerTest :
    DescribeSpec({
        describe("normalize") {
            it("strips a leading question phrase, filler words, and trailing punctuation") {
                GermanQueryNormalizer.normalize("Wo gibt es eine Grillwurst?") shouldBe "Grillwurst"
            }
            it("leaves a plain term unchanged") {
                GermanQueryNormalizer.normalize("Wein") shouldBe "Wein"
            }
            it("falls back to the trimmed input when stripping would leave nothing") {
                GermanQueryNormalizer.normalize("was") shouldBe "was"
            }
            it("returns an empty string unchanged") {
                GermanQueryNormalizer.normalize("") shouldBe ""
            }
            it("strips a shorter leading phrase without a longer overlapping match") {
                GermanQueryNormalizer.normalize("Wo ist DLRG?") shouldBe "DLRG"
            }
            it("does not strip a word that merely starts with a filler word") {
                GermanQueryNormalizer.normalize("Dessert") shouldBe "Dessert"
            }
        }
    })
