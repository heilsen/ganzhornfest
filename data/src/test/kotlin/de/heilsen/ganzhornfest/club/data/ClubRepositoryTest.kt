package de.heilsen.ganzhornfest.club.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.UUID

class ClubRepositoryTest :
    StringSpec({
        fun repositoryWithSubstringClubs(): ClubRepository {
            val driver = JdbcSqliteDriver("jdbc:sqlite:file:mem${UUID.randomUUID()}?mode=memory&cache=shared")
            GanzhornfestDb.Schema.create(driver)
            val db = GanzhornfestDb(driver)

            // Two clubs whose names are substrings of one another. The old LIKE query
            // resolved both when asked for "Sport".
            db.poiQueries.insert(id = 1, name = "Sport", typeId = 1)
            db.poiQueries.insert(id = 2, name = "Sportverein Neckarsulm", typeId = 1)
            db.offerQueries.insert(id = 10, typeId = 2, name = "Pommes", description = null)
            db.offerQueries.insert(id = 20, typeId = 2, name = "Waffeln", description = "mit Puderzucker")
            db.clubOfferQueries.insert(id = null, poiId = 1, offerId = 10)
            db.clubOfferQueries.insert(id = null, poiId = 2, offerId = 20)

            return ClubRepository(db)
        }

        "getOffersByClub resolves only the offers of the requested poi id" {
            val repository = repositoryWithSubstringClubs()

            runBlocking {
                repository.getOffersByClub(1).first() shouldBe
                    listOf(ClubOfferRow(offerId = 10, name = "Pommes", description = null))
                repository.getOffersByClub(2).first() shouldBe
                    listOf(ClubOfferRow(offerId = 20, name = "Waffeln", description = "mit Puderzucker"))
            }
        }

        "getClubsByOffer resolves only the clubs of the requested offer id" {
            val repository = repositoryWithSubstringClubs()

            runBlocking {
                repository.getClubsByOffer(10).first() shouldBe listOf(ClubRow(poiId = 1, name = "Sport"))
                repository.getClubsByOffer(20).first() shouldBe
                    listOf(ClubRow(poiId = 2, name = "Sportverein Neckarsulm"))
            }
        }

        "getClubName returns the row name for a poi id" {
            val repository = repositoryWithSubstringClubs()

            runBlocking {
                repository.getClubName(2).first() shouldBe "Sportverein Neckarsulm"
                repository.getClubName(999).first() shouldBe null
            }
        }
    })
