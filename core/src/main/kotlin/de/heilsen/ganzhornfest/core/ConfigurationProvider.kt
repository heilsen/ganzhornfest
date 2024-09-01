package de.heilsen.ganzhornfest.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale
import javax.inject.Inject

class ConfigurationProvider(private val configuration: Configuration) {
    constructor(resources: Resources) : this(resources.configuration)

    @Inject
    constructor(context: Context) : this(context.resources)

    fun getLocale(): Locale = if (Build.VERSION.SDK_INT >= 24) {
        configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        configuration.locale
    }
}