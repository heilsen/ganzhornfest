package de.heilsen.ganzhornfest

import android.app.Application
import de.heilsen.ganzhornfest.core.CrashReporter
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import de.heilsen.ganzhornfest.di.AppComponent
import de.heilsen.ganzhornfest.di.AppComponentProvider
import de.heilsen.ganzhornfest.di.appGraph
import de.heilsen.ganzhornfest.di.getValue
import de.heilsen.ganzhornfest.seed.FestivalDataSeeder
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraphFactory
import timber.log.Timber

interface EntryPoint {
    fun inject(ganzhornfestApplication: GanzhornfestApplication)
}

class GanzhornfestApplication :
    Application(),
    AppComponentProvider {
    override val appComponent: AppComponent by lazy {
        createGraphFactory<AppComponent.Factory>().create(this)
    }

    @Inject
    lateinit var timberTrees: Set<@JvmSuppressWildcards Timber.Tree>

    @Inject
    lateinit var festivalDataSeeder: FestivalDataSeeder

    @Inject
    lateinit var crashReporter: CrashReporter

    override fun onCreate() {
        super.onCreate()
        val entrypoint: EntryPoint by appGraph
        entrypoint.inject(this)
        Timber.plant(*timberTrees.toTypedArray())
        crashReporter.setCustomKey("db_schema_version", GanzhornfestDb.Schema.version.toString())
        festivalDataSeeder.seedIfNeeded()
    }
}
