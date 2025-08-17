package de.heilsen.ganzhornfest.search

import dagger.Binds
import dagger.Module

@Module
abstract class SearchModule {
    @Binds
    abstract fun bindUseCase(searchResultsUseCase: ShowSearchResultsUseCaseImpl): ShowSearchResultsUseCase
}