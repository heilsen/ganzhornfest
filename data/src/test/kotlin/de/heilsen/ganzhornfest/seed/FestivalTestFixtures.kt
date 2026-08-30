@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import de.heilsen.ganzhornfest.seed.model.FestivalData
import de.heilsen.ganzhornfest.seed.model.Manifest
import java.io.File
import java.io.InputStream
import kotlin.time.ExperimentalTime

internal fun parseShippedFestival(): Pair<Manifest, FestivalData> {
    val manifest = openShippedFestivalAsset("festival/manifest.json").use(::parseManifest)
    val data =
        openShippedFestivalAsset("festival/data.json").use { stream ->
            parseFestivalData(stream, manifest.year, manifest.timezone)
        }
    return manifest to data
}

internal fun openShippedFestivalAsset(name: String): InputStream {
    object {}
        .javaClass.classLoader
        ?.getResourceAsStream(name)
        ?.let { return it }
    val relative = "app/src/main/assets/$name"
    val file = listOf(File(relative), File("../$relative")).firstOrNull { it.isFile }
    return file?.inputStream() ?: error("Missing shipped $name")
}
