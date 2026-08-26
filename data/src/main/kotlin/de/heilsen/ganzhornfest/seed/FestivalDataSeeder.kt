@file:OptIn(ExperimentalSerializationApi::class, ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import android.content.Context
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.seed.model.FestivalData
import de.heilsen.ganzhornfest.seed.model.Manifest
import dev.zacsweers.metro.Inject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.modules.SerializersModule
import java.io.InputStream
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private const val MANIFEST_ASSET = "festival/manifest.json"
private const val DATA_ASSET = "festival/data.json"

class FestivalDataSeeder
    @Inject
    constructor(
        private val context: Context,
        private val db: GanzhornfestDb,
    ) {
        fun seedIfNeeded() {
            val manifest = context.assets.open(MANIFEST_ASSET).use(::parseManifest)
            val current = db.seedMetaQueries.currentVersion().executeAsOneOrNull()
            if (current != null && current >= manifest.dataVersion) return

            val data =
                context.assets.open(DATA_ASSET).use { stream ->
                    parseFestivalData(stream, manifest.year, manifest.timezone)
                }
            applyFestivalData(db, data, manifest.dataVersion)
        }
    }

internal val manifestJson = Json { ignoreUnknownKeys = true }

internal fun festivalDataJson(
    year: Int,
    timezone: String,
): Json =
    Json {
        ignoreUnknownKeys = true
        serializersModule =
            SerializersModule {
                contextual(Instant::class, FestivalInstantSerializer(year, timezone))
            }
    }

internal fun parseManifest(input: InputStream): Manifest = manifestJson.decodeFromStream(input)

internal fun parseFestivalData(
    input: InputStream,
    year: Int,
    timezone: String,
): FestivalData = festivalDataJson(year, timezone).decodeFromStream(input)

internal fun seedIfNeeded(
    db: GanzhornfestDb,
    manifest: Manifest,
    data: FestivalData,
) {
    val current = db.seedMetaQueries.currentVersion().executeAsOneOrNull()
    if (current != null && current >= manifest.dataVersion) return
    applyFestivalData(db, data, manifest.dataVersion)
}

internal fun applyFestivalData(
    db: GanzhornfestDb,
    data: FestivalData,
    dataVersion: Long,
) {
    db.transaction {
        db.clubOfferQueries.deleteAll()
        db.offerAliasQueries.deleteAll()
        db.poiCoordinateQueries.deleteAll()
        db.programQueries.deleteAll()
        db.busConnectionQueries.deleteAll()
        db.busLineQueries.deleteAll()
        db.offerQueries.deleteAll()
        db.offerTypeQueries.deleteAll()
        db.poiQueries.deleteAll()
        db.poiTypeQueries.deleteAll()
        db.coordinateQueries.deleteAll()

        data.poiTypes.forEach { db.poiTypeQueries.insert(it.id, it.name) }
        data.offerTypes.forEach { db.offerTypeQueries.insert(it.id, it.type) }
        data.coordinates.forEach { db.coordinateQueries.insert(it.id, it.lat, it.lng) }
        data.pois.forEach { db.poiQueries.insert(it.id, it.name, it.typeId) }
        data.poiCoordinates.forEach {
            db.poiCoordinateQueries.insert(it.id, it.poiId, it.coordinateId)
        }
        data.offers.forEach {
            db.offerQueries.insert(it.id, it.typeId, it.name, it.description)
        }
        data.offerAliases.forEach {
            db.offerAliasQueries.insert(it.id, it.offerId, it.alias)
        }
        data.clubOffers.forEach {
            db.clubOfferQueries.insert(it.id, it.poiId, it.offerId)
        }
        data.busLines.forEach {
            db.busLineQueries.insert(it.id, it.busLine, it.stops, it.destination)
        }
        data.busConnections.forEach {
            db.busConnectionQueries.insert(it.id, it.busLineId, it.departure.toString())
        }
        data.programs.forEach {
            db.programQueries.insert(
                it.id,
                it.name,
                it.description,
                it.start.toString(),
                it.end?.toString(),
                it.poiId,
            )
        }

        db.seedMetaQueries.setVersion(dataVersion)
    }
}
