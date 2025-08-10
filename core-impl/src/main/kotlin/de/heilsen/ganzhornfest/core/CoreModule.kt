package de.heilsen.ganzhornfest.core

import dagger.Binds
import dagger.Module

@Module
abstract class CoreModule {
    @Binds abstract fun bindConfigurationProvider(configurationProvider: ConfigurationProviderImpl): ConfigurationProvider
    @Binds abstract fun bindResourcesProvider(resourcesProvider: ResourcesProviderImpl): ResourcesProvider
    @Binds abstract fun bindGetOpeningDaysUseCase(GetOpeningDaysFor2024: GetOpeningDaysFor2024): GetOpeningDaysUseCase
    @Binds abstract fun bindSelectDefaultDateUseCase(selectDefaultDateUseCaseImpl: SelectDefaultDateUseCaseImpl): SelectDefaultDateUseCase
}