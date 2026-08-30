@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.seed.model.BusConnectionJson
import de.heilsen.ganzhornfest.seed.model.BusLineJson
import de.heilsen.ganzhornfest.seed.model.CoordinateJson
import de.heilsen.ganzhornfest.seed.model.FestivalData
import de.heilsen.ganzhornfest.seed.model.Manifest
import de.heilsen.ganzhornfest.seed.model.OfferJson
import de.heilsen.ganzhornfest.seed.model.OfferTypeJson
import de.heilsen.ganzhornfest.seed.model.PoiJson
import de.heilsen.ganzhornfest.seed.model.PoiTypeJson
import de.heilsen.ganzhornfest.seed.model.ProgramJson
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.util.UUID
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class FestivalDataSeederTest :
    DescribeSpec({
        fun openDb(): GanzhornfestDb {
            val driver = JdbcSqliteDriver("jdbc:sqlite:file:mem${UUID.randomUUID()}?mode=memory&cache=shared")
            GanzhornfestDb.Schema.create(driver)
            return GanzhornfestDb(driver)
        }

        it("skips reload when current version is newer") {
            val db = openDb()
            val currentData = sampleFestivalData(poiName = "Hauptbühne Museumsplatz")
            val staleData = sampleFestivalData(poiName = "Old Stage")

            seedIfNeeded(db, Manifest(2026, 2, "+02:00"), currentData)
            seedIfNeeded(db, Manifest(2026, 1, "+02:00"), staleData)

            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldContain
                "Hauptbühne Museumsplatz"
            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldNotContain "Old Stage"
            db.seedMetaQueries.currentVersion().executeAsOne() shouldBe 2L
        }

        it("skips reload when current version equals dataVersion") {
            val db = openDb()
            val currentData = sampleFestivalData(poiName = "Hauptbühne Museumsplatz")
            val staleData = sampleFestivalData(poiName = "Old Stage")

            seedIfNeeded(db, Manifest(2026, 2, "+02:00"), currentData)
            seedIfNeeded(db, Manifest(2026, 2, "+02:00"), staleData)

            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldContain
                "Hauptbühne Museumsplatz"
            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldNotContain "Old Stage"
            db.seedMetaQueries.currentVersion().executeAsOne() shouldBe 2L
        }

        it("wipes and reloads when dataVersion is newer") {
            val db = openDb()
            val oldData =
                sampleFestivalData(
                    poiName = "Old Stage",
                    programName = "Old Show",
                )
            val newData =
                sampleFestivalData(
                    poiName = "Hauptbühne Museumsplatz",
                    programName = "Eröffnung des Festes",
                )

            seedIfNeeded(db, Manifest(2026, 1, "+02:00"), oldData)
            seedIfNeeded(db, Manifest(2026, 2, "+02:00"), newData)

            db.programQueries.countAll().executeAsOne() shouldBe 1L
            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldContain
                "Hauptbühne Museumsplatz"
            db.poiQueries
                .selectAll()
                .executeAsList()
                .map { it.name } shouldNotContain "Old Stage"
            db.seedMetaQueries.currentVersion().executeAsOne() shouldBe 2L
        }

        it("seeds shipped data.json so programs and bus rows survive a datetime window") {
            val db = openDb()
            val (manifest, data) = parseShippedFestival()

            data.programs.size shouldBeGreaterThan 0
            data.offerAliases.size shouldBeGreaterThan 0
            data.poiTypes.map { it.name } shouldContain "attraction"

            seedIfNeeded(db, manifest, data)

            db.programQueries.countAll().executeAsOne() shouldBe data.programs.size.toLong()

            val opening = data.programs.first().start
            db.programQueries
                .getPrograms(
                    "%",
                    (opening - 1.hours).toString(),
                    (opening + 1.hours).toString(),
                ).executeAsList()
                .shouldNotBeEmpty()

            val connection = data.busConnections.first()
            val destination =
                data.busLines
                    .first { it.id == connection.busLineId }
                    .destination
            db.busConnectionQueries
                .getBusConnection(
                    destination,
                    (connection.departure - 1.hours).toString(),
                    (connection.departure + 1.hours).toString(),
                ).executeAsList()
                .shouldNotBeEmpty()
        }
    })

private fun sampleFestivalData(
    poiName: String,
    programName: String = "Eröffnung des Festes",
): FestivalData =
    FestivalData(
        poiTypes = listOf(PoiTypeJson(2, "event location")),
        offerTypes = listOf(OfferTypeJson(1, "food")),
        coordinates = listOf(CoordinateJson(101, 49.192, 9.222)),
        pois = listOf(PoiJson(101, poiName, 2)),
        poiCoordinates = emptyList(),
        offers = listOf(OfferJson(1, 1, "Bier", null)),
        clubOffers = emptyList(),
        busLines = listOf(BusLineJson(1, "91", "Amorbach", "Dahenfeld")),
        busConnections =
            listOf(
                BusConnectionJson(null, 1, Instant.parse("2026-09-07T19:18:00+02:00")),
            ),
        programs =
            listOf(
                ProgramJson(
                    id = 1,
                    name = programName,
                    description = null,
                    start = Instant.parse("2026-09-05T16:00:00+02:00"),
                    end = null,
                    poiId = 101,
                ),
            ),
    )
