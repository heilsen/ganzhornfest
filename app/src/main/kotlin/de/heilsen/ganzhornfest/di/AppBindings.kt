package de.heilsen.ganzhornfest.di

import android.app.Application
import android.content.Context
import de.heilsen.ganzhornfest.GanzhornfestApplication
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
interface AppBindings {
    @Provides fun provideApplication(app: GanzhornfestApplication): Application = app
    @Provides fun provideContext(app: GanzhornfestApplication): Context = app
}
