package de.heilsen.ganzhornfest.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import de.heilsen.ganzhornfest.GanzhornfestApplication
import de.heilsen.ganzhornfest.bus.BusModule
import de.heilsen.ganzhornfest.core.CoreModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TimberModule::class,
        BusModule::class,
        CoreModule::class,
        GanzhornfestDbModule::class
    ]
)
interface AppComponent :
    de.heilsen.ganzhornfest.main.EntryPoint,
    de.heilsen.ganzhornfest.EntryPoint,
    de.heilsen.ganzhornfest.search.EntryPoint {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance ganzhornfestApplication: GanzhornfestApplication,
            @BindsInstance application: Application,
            @BindsInstance context: Context,
        ): AppComponent
    }
}