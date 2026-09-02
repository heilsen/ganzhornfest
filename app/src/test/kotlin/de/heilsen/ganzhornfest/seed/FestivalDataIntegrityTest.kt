@file:OptIn(ExperimentalSerializationApi::class, ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import de.heilsen.ganzhornfest.seed.model.FestivalData
import de.heilsen.ganzhornfest.seed.model.Manifest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.modules.SerializersModule
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Guards the shipped app/src/main/assets/festival/data.json against the kind of hand-edit
// mistake that dropped attraction markers from the map: a row referencing an id that no
// other table defines. FestivalDataSeeder.applyFestivalData silently drops such rows instead
// of failing, so nothing else catches this.
class FestivalDataIntegrityTest :
    StringSpec({
        "every foreign key in the shipped seed data resolves" {
            val data = loadFestivalData()

            val poiTypeIds = data.poiTypes.map { it.id }.toSet()
            val offerTypeIds = data.offerTypes.map { it.id }.toSet()
            val coordinateIds = data.coordinates.map { it.id }.toSet()
            val poiIds = data.pois.map { it.id }.toSet()
            val offerIds = data.offers.map { it.id }.toSet()
            val busLineIds = data.busLines.map { it.id }.toSet()

            val problems =
                buildList {
                    data.pois
                        .filter { it.typeId !in poiTypeIds }
                        .forEach { add("poi ${it.id} has unknown typeId ${it.typeId}") }
                    data.offers
                        .filter { it.typeId !in offerTypeIds }
                        .forEach { add("offer ${it.id} has unknown typeId ${it.typeId}") }
                    data.poiCoordinates
                        .filter { it.poiId !in poiIds }
                        .forEach { add("poiCoordinate ${it.id} has unknown poiId ${it.poiId}") }
                    data.poiCoordinates
                        .filter { it.coordinateId !in coordinateIds }
                        .forEach { add("poiCoordinate ${it.id} has unknown coordinateId ${it.coordinateId}") }
                    data.clubOffers
                        .filter { it.poiId !in poiIds }
                        .forEach { add("clubOffer ${it.id} has unknown poiId ${it.poiId}") }
                    data.clubOffers
                        .filter { it.offerId !in offerIds }
                        .forEach { add("clubOffer ${it.id} has unknown offerId ${it.offerId}") }
                    data.offerAliases
                        .filter { it.offerId !in offerIds }
                        .forEach { add("offerAlias ${it.id} has unknown offerId ${it.offerId}") }
                    data.programs
                        .filter { it.poiId !in poiIds }
                        .forEach { add("program ${it.id} has unknown poiId ${it.poiId}") }
                    data.busConnections
                        .filter { it.busLineId !in busLineIds }
                        .forEach { add("busConnection ${it.id} has unknown busLineId ${it.busLineId}") }
                }

            problems shouldBe emptyList()
        }

        // The stage marker to Programmplan deep link matches an EVENT_LOCATION marker title
        // against a "Bühne" dropdown entry by exact string. GetMarkersUseCase tags a marker
        // EVENT_LOCATION by the poiType name "event location". Poi.sq selectStages fills that
        // dropdown from poi rows with the hardcoded typeId = 2. Renumber the seed and stage
        // markers silently stop matching dropdown entries, with no compile error.
        "stage pois keep the type id and name that map markers and selectStages both depend on" {
            val data = loadFestivalData()

            val eventLocation = data.poiTypes.single { it.name == "event location" }

            eventLocation.id shouldBe 2L
        }

        // Grenzenlose Tierhilfe and Herzmahl e.V. both fell out of the seed when content moved
        // from 1.sqm to data.json, and no test noticed. A dropped club takes its clubOffer and
        // poiCoordinate rows with it, so nothing is left pointing at a missing id and the
        // foreign key check above stays green. Guard the other direction: a club with no offers
        // renders an empty detail sheet, and a club with no coordinate never gets a map pin.
        "every club poi has offers and a place on the map" {
            val data = loadFestivalData()

            val clubPoiTypeId = data.poiTypes.single { it.name == "club" }.id
            val clubs = data.pois.filter { it.typeId == clubPoiTypeId }
            val poiIdsWithOffers = data.clubOffers.map { it.poiId }.toSet()
            val poiIdsWithCoordinates = data.poiCoordinates.map { it.poiId }.toSet()

            val problems =
                buildList {
                    clubs
                        .filter { it.id !in poiIdsWithOffers }
                        .forEach { add("club ${it.id} (${it.name}) has no clubOffer row") }
                    clubs
                        .filter { it.id !in poiIdsWithCoordinates }
                        .forEach { add("club ${it.id} (${it.name}) has no poiCoordinate row") }
                }

            problems shouldBe emptyList()
        }
    })

private fun loadFestivalData(): FestivalData {
    val classLoader = checkNotNull(FestivalDataIntegrityTest::class.java.classLoader)
    val manifest =
        Json { ignoreUnknownKeys = true }
            .decodeFromStream<Manifest>(
                checkNotNull(classLoader.getResourceAsStream("festival/manifest.json")),
            )
    val json =
        Json {
            ignoreUnknownKeys = true
            serializersModule =
                SerializersModule {
                    contextual(Instant::class, FestivalInstantSerializer(manifest.year, manifest.timezone))
                }
        }
    return json.decodeFromStream(
        checkNotNull(classLoader.getResourceAsStream("festival/data.json")),
    )
}
