package de.heilsen.ganzhornfest.di

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
}
