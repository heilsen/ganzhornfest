package de.heilsen.ganzhornfest

import android.app.Application
import de.heilsen.ganzhornfest.di.AppComponent
import de.heilsen.ganzhornfest.di.AppComponentProvider
import de.heilsen.ganzhornfest.di.appGraph
import de.heilsen.ganzhornfest.di.getValue
import dev.zacsweers.metro.createGraphFactory
import timber.log.Timber
import dev.zacsweers.metro.Inject

interface EntryPoint {
    fun inject(ganzhornfestApplication: GanzhornfestApplication)
}

class GanzhornfestApplication : Application(), AppComponentProvider {

    override val appComponent: AppComponent by lazy {
        createGraphFactory<AppComponent.Factory>().create(this)
    }

    @Inject
    lateinit var timberTrees: Set<@JvmSuppressWildcards Timber.Tree>

    override fun onCreate() {
        super.onCreate()
        val entrypoint: EntryPoint by appGraph
        entrypoint.inject(this)
        Timber.plant(*timberTrees.toTypedArray())
    }
}
