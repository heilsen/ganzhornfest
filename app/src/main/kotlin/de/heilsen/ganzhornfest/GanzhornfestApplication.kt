package de.heilsen.ganzhornfest

import android.app.Application
import com.squareup.anvil.annotations.ContributesTo
import de.heilsen.ganzhornfest.di.AppComponent
import de.heilsen.ganzhornfest.di.AppComponentProvider
import de.heilsen.ganzhornfest.di.AppScope
import de.heilsen.ganzhornfest.di.DaggerAppComponent
import de.heilsen.ganzhornfest.di.appScope
import de.heilsen.ganzhornfest.di.getValue
import timber.log.Timber
import java.security.KeyStore.Entry
import javax.inject.Inject

@ContributesTo(AppScope::class)
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
