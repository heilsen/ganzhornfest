package de.heilsen.ganzhornfest.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import de.heilsen.ganzhornfest.GanzhornfestApplication

@Module
abstract class AppModule {
    @Binds
    abstract fun bindApplication(ganzhornfestApplication: GanzhornfestApplication): Application

    @Binds
    abstract fun bindContext(ganzhornfestApplication: GanzhornfestApplication): Context
}