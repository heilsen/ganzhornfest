package de.heilsen.ganzhornfest.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.util.Locale
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class ConfigurationProviderImpl(private val configuration: Configuration): ConfigurationProvider {
    constructor(resources: Resources) : this(resources.configuration)

    @Inject
    constructor(context: Context) : this(context.resources)

    override fun getLocale(): Locale = if (Build.VERSION.SDK_INT >= 24) {
        configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        configuration.locale
    }
}

