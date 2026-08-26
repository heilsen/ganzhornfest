@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class Manifest(
    val year: Int,
    val dataVersion: Long,
    val timezone: String,
)

@Serializable
data class FestivalData(
    val poiTypes: List<PoiTypeJson>,
    val offerTypes: List<OfferTypeJson>,
    val coordinates: List<CoordinateJson>,
    val pois: List<PoiJson>,
    val poiCoordinates: List<PoiCoordinateJson>,
    val offers: List<OfferJson>,
    val offerAliases: List<OfferAliasJson> = emptyList(),
    val clubOffers: List<ClubOfferJson>,
    val busLines: List<BusLineJson>,
    val busConnections: List<BusConnectionJson>,
    val programs: List<ProgramJson>,
)

@Serializable
data class PoiTypeJson(
    val id: Long,
    val name: String,
)

@Serializable
data class OfferTypeJson(
    val id: Long,
    val type: String,
)

@Serializable
data class CoordinateJson(
    val id: Long,
    val lat: Double,
    val lng: Double,
)

@Serializable
data class PoiJson(
    val id: Long,
    val name: String,
    val typeId: Long,
)

@Serializable
data class PoiCoordinateJson(
    val id: Long? = null,
    val poiId: Long,
    val coordinateId: Long,
)

@Serializable
data class OfferJson(
    val id: Long,
    val typeId: Long,
    val name: String,
    val description: String? = null,
)

@Serializable
data class OfferAliasJson(
    val id: Long? = null,
    val offerId: Long,
    val alias: String,
)

@Serializable
data class ClubOfferJson(
    val id: Long? = null,
    val poiId: Long,
    val offerId: Long,
)

@Serializable
data class BusLineJson(
    val id: Long,
    val busLine: String,
    val stops: String? = null,
    val destination: String,
)

@Serializable
data class BusConnectionJson(
    val id: Long? = null,
    val busLineId: Long,
    @Contextual val departure: Instant,
)

@Serializable
data class ProgramJson(
    val id: Long,
    val name: String,
    val description: String? = null,
    @Contextual val start: Instant,
    @Contextual val end: Instant? = null,
    val poiId: Long,
)
