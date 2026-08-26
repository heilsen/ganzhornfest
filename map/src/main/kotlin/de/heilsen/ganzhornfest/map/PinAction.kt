package de.heilsen.ganzhornfest.map

fun MarkerUiType.isActionable(): Boolean =
    when (this) {
        MarkerUiType.CLUB,
        MarkerUiType.EVENT_LOCATION,
        MarkerUiType.PLAYGROUND,
        MarkerUiType.BUS_STOP,
        -> true
        MarkerUiType.ATTRACTION,
        MarkerUiType.WC,
        MarkerUiType.FIRST_AID,
        -> false
    }

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
