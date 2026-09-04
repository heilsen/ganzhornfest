package de.heilsen.ganzhornfest.detail

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class DetailPresenterTest :
    DescribeSpec({
        describe("DetailPresenter") {
            it("passes a successful club load through unchanged") {
                val success =
                    DetailModel.Success(
                        title = "Sängerbund",
                        target = DetailTarget.Club(1),
                        items = listOf(DetailItem("Bier", "vom Fass", DetailTarget.Offer(10))),
                    )
                val getClubDetail =
                    mockk<GetClubDetailUseCase> {
                        every { this@mockk(1) } returns flowOf(success)
                    }
                val presenter =
                    DetailPresenter(getClubDetail, mockk(), mockk(), mockk())
                val events = MutableSharedFlow<DetailEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        awaitItem() shouldBe DetailModel.Loading
                        events.emit(DetailEvent.Open(DetailTarget.Club(1)))
                        runCurrent()

                        expectMostRecentItem() shouldBe success

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            it("maps a thrown query to Error instead of hanging on Loading") {
                val getClubDetail =
                    mockk<GetClubDetailUseCase> {
                        every { this@mockk(1) } returns
                            flow { throw IllegalStateException("db gone") }
                    }
                val presenter =
                    DetailPresenter(getClubDetail, mockk(), mockk(), mockk())
                val events = MutableSharedFlow<DetailEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        awaitItem() shouldBe DetailModel.Loading
                        events.emit(DetailEvent.Open(DetailTarget.Club(1)))
                        runCurrent()

                        expectMostRecentItem() shouldBe DetailModel.Error

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    })
