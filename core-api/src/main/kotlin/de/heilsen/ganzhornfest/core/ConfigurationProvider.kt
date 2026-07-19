package de.heilsen.ganzhornfest.core

import java.util.Locale

interface ConfigurationProvider {
    fun getLocale(): Locale
}
