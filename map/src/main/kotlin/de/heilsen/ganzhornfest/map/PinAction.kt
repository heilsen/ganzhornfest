package de.heilsen.ganzhornfest.map

enum class PinEmphasis {
    Default,
    Highlighted,
    Dimmed,
}

fun resolvePinEmphasis(
    title: String,
    highlightedTitles: Set<String>?,
): PinEmphasis {
    if (highlightedTitles.isNullOrEmpty()) return PinEmphasis.Default
    return if (title in highlightedTitles) PinEmphasis.Highlighted else PinEmphasis.Dimmed
}
