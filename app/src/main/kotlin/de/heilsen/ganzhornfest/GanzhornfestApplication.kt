package de.heilsen.ganzhornfest

import android.app.Application
import de.heilsen.ganzhornfest.di.AppComponent
import de.heilsen.ganzhornfest.di.AppComponentProvider
import de.heilsen.ganzhornfest.di.DaggerAppComponent
import de.heilsen.ganzhornfest.di.appScope
import de.heilsen.ganzhornfest.di.getValue
import timber.log.Timber
import javax.inject.Inject

interface EntryPoint {
    fun inject(ganzhornfestApplication: GanzhornfestApplication)
}

class GanzhornfestApplication : Application(), AppComponentProvider {

    override val appComponent: AppComponent by lazy { DaggerAppComponent.factory().create(this) }

    @Inject
    lateinit var timberTrees: Set<@JvmSuppressWildcards Timber.Tree>

    override fun onCreate() {
        super.onCreate()
        val entrypoint: EntryPoint by appScope
        entrypoint.inject(this)
        Timber.plant(*timberTrees.toTypedArray())
    }
}
