package de.heilsen.ganzhornfest.di

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
@BindingContainer
object DatabaseBindings {
    @Provides
    fun database(application: Application): GanzhornfestDb {
        val driver =
            AndroidSqliteDriver(
                schema = GanzhornfestDb.Schema,
                context = application,
                name = "ganzhornfest.db",
                useNoBackupDirectory = true,
            )
        return GanzhornfestDb(driver)
    }
}
