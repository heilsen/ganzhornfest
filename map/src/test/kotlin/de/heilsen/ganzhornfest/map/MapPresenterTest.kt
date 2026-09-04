package de.heilsen.ganzhornfest.map

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class MapPresenterTest :
    DescribeSpec({
        fun presenter(getMarkers: GetMarkersUseCase): MapPresenter {
            val getClubPins =
                mockk<GetClubPinsUseCase> {
                    every { this@mockk() } returns flowOf(persistentListOf())
                }
            return MapPresenter(getMarkers, getClubPins, mockk(relaxed = true))
        }

        describe("MapPresenter") {
            it("stays on Loading until the marker query emits") {
                val getMarkers =
                    mockk<GetMarkersUseCase> {
                        every { this@mockk() } returns MutableSharedFlow()
                    }

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter(getMarkers).present() }.test {
                        awaitItem().shouldBeInstanceOf<MapModel.Loading>()
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            it("maps a thrown marker query to Error") {
                val getMarkers =
                    mockk<GetMarkersUseCase> {
                        every { this@mockk() } returns flow { throw IllegalStateException("db gone") }
                    }

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter(getMarkers).present() }.test {
                        runCurrent()
                        expectMostRecentItem() shouldBe MapModel.Error
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            it("emits Data once the marker query delivers") {
                val getMarkers =
                    mockk<GetMarkersUseCase> {
                        every { this@mockk() } returns flowOf(persistentSetOf())
                    }

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter(getMarkers).present() }.test {
                        runCurrent()
                        expectMostRecentItem().shouldBeInstanceOf<MapModel.Data>()
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    })
