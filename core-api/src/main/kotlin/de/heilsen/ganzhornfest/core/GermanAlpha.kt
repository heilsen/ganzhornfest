package de.heilsen.ganzhornfest.core

import java.text.Collator
import java.util.Locale

fun germanAlphaComparator(): Comparator<String> {
    val collator = Collator.getInstance(Locale.GERMAN)
    collator.strength = Collator.PRIMARY
    return Comparator { left, right -> collator.compare(left, right) }
}
