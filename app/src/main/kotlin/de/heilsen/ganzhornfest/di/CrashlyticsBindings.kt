package de.heilsen.ganzhornfest.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import de.heilsen.ganzhornfest.core.CrashReporter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
@BindingContainer
object CrashlyticsBindings {
    @Provides
    fun firebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
}

@ContributesBinding(AppScope::class)
class CrashReporterImpl
    @Inject
    constructor(
        private val crashlytics: FirebaseCrashlytics,
    ) : CrashReporter {
        override fun recordNonFatal(
            throwable: Throwable,
            tag: String,
        ) {
            crashlytics.log(tag)
            crashlytics.recordException(throwable)
        }

        override fun setCustomKey(
            key: String,
            value: String,
        ) {
            crashlytics.setCustomKey(key, value)
        }
    }
