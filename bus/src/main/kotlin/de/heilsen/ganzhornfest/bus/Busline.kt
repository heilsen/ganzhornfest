package de.heilsen.ganzhornfest.bus

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Busline(
    val line: String,
    val destination: String,
    val stops: ImmutableList<String> = persistentListOf()
)