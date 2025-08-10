package de.heilsen.ganzhornfest.di

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import de.heilsen.ganzhornfest.database.GanzhornfestDb

@Module
object GanzhornfestDbModule {
    @Provides
    fun database(application: Application): GanzhornfestDb {
        val driver = AndroidSqliteDriver(GanzhornfestDb.Schema, application, "ganzhornfest.db")
        return GanzhornfestDb(driver)
    }
}