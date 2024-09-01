package de.heilsen.ganzhornfest.di

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import de.heilsen.ganzhornfest.database.GanzhornfestDb

@ContributesTo(AppScope::class)
@Module
class GanzhornfestDbModule {
    @Provides
    fun database(application: Application): GanzhornfestDb {
        val driver = AndroidSqliteDriver(GanzhornfestDb.Schema, application, "ganzhornfest.db")
        return GanzhornfestDb(driver)
    }
}