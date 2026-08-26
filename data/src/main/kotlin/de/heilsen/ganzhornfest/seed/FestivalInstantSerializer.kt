@file:OptIn(ExperimentalTime::class)

package de.heilsen.ganzhornfest.seed

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class FestivalInstantSerializer(
    private val year: Int,
    private val timezone: String,
) : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FestivalInstant", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant {
        val raw = decoder.decodeString()
        val iso = "$year-$raw:00$timezone"
        return Instant.parse(iso)
    }

    override fun serialize(
        encoder: Encoder,
        value: Instant,
    ) {
        error("FestivalInstantSerializer is read-only")
    }
}
