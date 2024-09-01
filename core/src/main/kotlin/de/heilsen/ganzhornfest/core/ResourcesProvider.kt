package de.heilsen.ganzhornfest.core

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val context: Context) {
    fun getString(@StringRes id: Int): String = context.resources.getString(id)
}