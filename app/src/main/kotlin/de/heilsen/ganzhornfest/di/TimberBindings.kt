package de.heilsen.ganzhornfest.di

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import de.heilsen.ganzhornfest.BuildConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides
import timber.log.Timber

@ContributesTo(AppScope::class)
interface TimberMultibinding {
    @Multibinds(allowEmpty = true)
    fun timberTrees(): Set<@JvmSuppressWildcards Timber.Tree>
}

@ContributesTo(AppScope::class)
@BindingContainer
object TimberBindings {
    @Provides
    @ElementsIntoSet
    fun debugTrees(): Set<@JvmSuppressWildcards Timber.Tree> =
        buildSet {
            if (BuildConfig.DEBUG) add(Timber.DebugTree())
        }

    @Provides
    @ElementsIntoSet
    fun crashlyticsTrees(crashlytics: FirebaseCrashlytics): Set<@JvmSuppressWildcards Timber.Tree> = setOf(CrashlyticsTree(crashlytics))
}

// Forwards WARN and ERROR logs to Crashlytics as breadcrumbs and non-fatals. DEBUG and INFO are
// excluded so user-entered search terms (see ShowSearchResultsUseCaseImpl) never leave the device.
class CrashlyticsTree(
    private val crashlytics: FirebaseCrashlytics,
) : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?,
    ) {
        if (priority < Log.WARN) return
        crashlytics.log("$tag: $message")
        if (t != null) crashlytics.recordException(t)
    }
}
