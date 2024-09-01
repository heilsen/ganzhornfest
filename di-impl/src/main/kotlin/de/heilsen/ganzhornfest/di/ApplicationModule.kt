package de.heilsen.ganzhornfest.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module

@Module
@ContributesTo(AppScope::class)
interface ApplicationModule {
    @Binds
    fun bindApplication(application: Application): Context
}