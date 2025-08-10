package de.heilsen.ganzhornfest.core

import android.content.Context
import androidx.annotation.StringRes
import com.squareup.anvil.annotations.ContributesBinding
import de.heilsen.ganzhornfest.di.AppScope
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class ResourcesProviderImpl @Inject constructor(private val context: Context): ResourcesProvider {
    override fun getString(@StringRes id: Int): String = context.resources.getString(id)
}