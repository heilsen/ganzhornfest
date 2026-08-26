package de.heilsen.ganzhornfest.map

import com.google.android.gms.maps.model.LatLng

data class ClubPin(
    val poiId: Long,
    val coordinateId: Long?,
    val name: String,
    val chipLabel: String,
    val latLng: LatLng?,
)

internal fun chipLabel(
    poiId: Long,
    name: String,
    coordinateId: Long?,
): String {
    extraPinLabel[coordinateId]?.let { return it }
    val shortName = name.removePrefix("Sport-Union Neckarsulm - ")
    val flyer = flyerNumber[poiId]
    return if (flyer != null) "$flyer $shortName" else shortName
}

internal fun clubPinsToSql(pins: List<ClubPin>): String {
    val rows =
        pins.mapNotNull { pin ->
            val id = pin.coordinateId ?: return@mapNotNull null
            val latLng = pin.latLng ?: return@mapNotNull null
            Triple(pin.chipLabel, id, latLng)
        }
    val inserts =
        rows.joinToString("\n") { (label, id, latLng) ->
            "-- $label\nINSERT INTO coordinate VALUES($id,${formatCoord(latLng.latitude)},${formatCoord(latLng.longitude)});"
        }
    val updates =
        rows.joinToString("\n") { (label, id, latLng) ->
            "-- $label\nUPDATE coordinate SET lat = ${formatCoord(
                latLng.latitude,
            )}, lng = ${formatCoord(latLng.longitude)} WHERE id = $id;"
        }
    return """
            |-- 1) Neuinstallation: passende INSERT-Zeilen ersetzen in
            |-- database/src/main/sqldelight/de/heilsen/ganzhornfest/database/Coordinate.sq
            |$inserts
            |
            |-- 2) Bestehende Apps: ans Ende anhängen von
            |-- database/src/main/sqldelight/migrations/3.sqm
            |-- UPDATE wirkt auch für die neuen IDs 49/50, wenn der INSERT darüber schon lief.
            |$updates
        """.trimMargin()
}

private fun formatCoord(value: Double): String = "%.10f".format(java.util.Locale.US, value)

private val extraPinLabel =
    mapOf(
        42L to "G Schnappfalle",
        46L to "H Ponyreiten",
        101L to "B Hauptbühne",
        102L to "C Bühne Karlsplatz",
        201L to "D Karussell",
        301L to "WC Urban-/Engelstraße",
        302L to "WC Stadtmauer",
        303L to "WC Urban-/Schulgasse",
        304L to "WC Schindlerstraße",
        401L to "A Erste Hilfe (ASB)",
        501L to "ZOB (Ballei)",
    )

private val flyerNumber =
    mapOf(
        1L to "01",
        2L to "02",
        3L to "03",
        4L to "04",
        5L to "05",
        6L to "06",
        42L to "07",
        7L to "08",
        9L to "09",
        10L to "10",
        11L to "11",
        12L to "12",
        14L to "13",
        15L to "14",
        17L to "15",
        16L to "16",
        18L to "17",
        20L to "18",
        22L to "19",
        23L to "20",
        24L to "21",
        25L to "22",
        27L to "23",
        28L to "24",
        29L to "25",
        40L to "26",
        31L to "27",
        32L to "28",
        34L to "29",
        35L to "30",
        36L to "31",
        37L to "32",
        38L to "33",
        39L to "34",
        201L to "D",
        43L to "E",
    )
