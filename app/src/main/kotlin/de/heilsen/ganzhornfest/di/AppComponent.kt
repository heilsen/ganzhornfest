package de.heilsen.ganzhornfest.di

import de.heilsen.ganzhornfest.GanzhornfestApplication
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class)
interface AppComponent :
    de.heilsen.ganzhornfest.main.EntryPoint,
    de.heilsen.ganzhornfest.EntryPoint,
    de.heilsen.ganzhornfest.search.EntryPoint {
    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides ganzhornfestApplication: GanzhornfestApplication,
        ): AppComponent
    }
}
