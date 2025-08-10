package de.heilsen.ganzhornfest.core

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProviderImpl @Inject constructor(private val context: Context): ResourcesProvider {
    override fun getString(@StringRes id: Int): String = context.resources.getString(id)
}