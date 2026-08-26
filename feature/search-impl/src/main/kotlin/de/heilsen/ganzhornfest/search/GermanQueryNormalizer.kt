package de.heilsen.ganzhornfest.search

import java.util.Locale

private val LEADING_PHRASES =
    listOf(
        "wo gibt es",
        "wo gibt's",
        "wo bekomme ich",
        "wo kann ich",
        "wo finde ich",
        "wo kriege ich",
        "ich hätte gerne",
        "ich möchte",
        "ich suche",
        "gibt es",
        "hat jemand",
        "wer verkauft",
        "wer hat",
        "wo ist",
        "wo sind",
    ).sortedByDescending { it.split(" ").size }

private val FILLER_WORDS =
    setOf(
        "ein",
        "eine",
        "einen",
        "einem",
        "einer",
        "der",
        "die",
        "das",
        "den",
        "dem",
        "etwas",
        "was",
        "mal",
        "bitte",
        "noch",
    )

/**
 * Strips German question phrasing ("Wo gibt es...") and filler words from a spoken or
 * typed search query, so "Wo gibt es eine Grillwurst?" becomes "Grillwurst" before it
 * hits the database. Falls back to the raw trimmed input if stripping would leave
 * nothing, so a query like "was" still searches instead of listing everything.
 */
internal object GermanQueryNormalizer {
    fun normalize(query: String): String {
        val trimmed = query.trim().trimEnd('?', '!', '.').trim()
        if (trimmed.isEmpty()) return trimmed

        val words = trimmed.split(Regex("\\s+"))
        val lowerWords = words.map { it.lowercase(Locale.GERMAN) }

        val matchedPhrase =
            LEADING_PHRASES.firstOrNull { phrase ->
                val phraseWords = phrase.split(" ")
                lowerWords.size >= phraseWords.size && lowerWords.take(phraseWords.size) == phraseWords
            }
        val remainingWords =
            if (matchedPhrase != null) {
                words.drop(matchedPhrase.split(" ").size)
            } else {
                words
            }

        val filtered = remainingWords.filter { it.lowercase(Locale.GERMAN) !in FILLER_WORDS }

        return filtered.joinToString(" ").ifBlank { trimmed }
    }
}
