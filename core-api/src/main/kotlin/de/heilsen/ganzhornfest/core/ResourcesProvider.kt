package de.heilsen.ganzhornfest.core

import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getString(@StringRes id: Int): String
}