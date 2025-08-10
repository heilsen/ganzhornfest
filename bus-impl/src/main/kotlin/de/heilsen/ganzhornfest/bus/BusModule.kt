package de.heilsen.ganzhornfest.bus

import dagger.Binds
import dagger.Module

@Module
abstract class BusModule {
    @Binds
    abstract fun bindGetBusConnectionUseCase(getBusConnectionsUseCaseImpl: GetBusConnectionsUseCaseImpl): GetBusConnectionsUseCase
}