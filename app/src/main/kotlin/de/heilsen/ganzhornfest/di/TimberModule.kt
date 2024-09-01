package de.heilsen.ganzhornfest.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.Multibinds
import de.heilsen.ganzhornfest.BuildConfig
import timber.log.Timber

@Module
@ContributesTo(AppScope::class)
abstract class TimberModule {

    @Multibinds
    abstract fun timberTrees(): Set<@JvmSuppressWildcards Timber.Tree>


    companion object {
        @Provides
        @ElementsIntoSet
        fun debugTree(): Set<@JvmSuppressWildcards Timber.Tree> = buildSet {
            if (BuildConfig.DEBUG) add(Timber.DebugTree())
        }
    }

}