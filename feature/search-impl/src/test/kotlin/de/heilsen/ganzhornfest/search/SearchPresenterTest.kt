package de.heilsen.ganzhornfest.search

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPresenterTest :
    DescribeSpec({
        describe("SearchPresenter") {
            it("only queries the final query after rapid successive Search events") {
                val showResults = mockk<ShowSearchResultsUseCase>()
                every { showResults("b", persistentSetOf(Category.Club)) } returns
                    flowOf(persistentListOf(SearchModel.Result("b-result", "", Category.Club)))
                every { showResults("ba", persistentSetOf(Category.Club)) } returns
                    flowOf(persistentListOf(SearchModel.Result("ba-result", "", Category.Club)))
                val presenter = SearchPresenter(showResults)
                val events = MutableSharedFlow<SearchEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        events.emit(SearchEvent.Search("b"))
                        runCurrent()
                        advanceTimeBy(100)
                        events.emit(SearchEvent.Search("ba"))
                        runCurrent()
                        advanceTimeBy(301)
                        runCurrent()

                        val model = expectMostRecentItem() as SearchModel.Data
                        model.results shouldBe
                            persistentListOf(SearchModel.Result("ba-result", "", Category.Club))

                        cancelAndIgnoreRemainingEvents()
                    }
                }

                verify(exactly = 0) { showResults.invoke("b", persistentSetOf(Category.Club)) }
            }

            it("keeps the query when the overlay stays expanded") {
                val showResults = mockk<ShowSearchResultsUseCase>()
                every { showResults("", persistentSetOf(Category.Club)) } returns flowOf(persistentListOf())
                every { showResults("grill", persistentSetOf(Category.Club)) } returns
                    flowOf(persistentListOf())
                val presenter = SearchPresenter(showResults)
                val events = MutableSharedFlow<SearchEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        events.emit(SearchEvent.Search("grill"))
                        events.emit(SearchEvent.SetExpanded(true))
                        runCurrent()

                        val model = expectMostRecentItem() as SearchModel.Data
                        model.query shouldBe "grill"
                        model.expanded shouldBe true

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            it("keeps the session when SearchBar tears down after opening a result") {
                val showResults = mockk<ShowSearchResultsUseCase>()
                every { showResults("", persistentSetOf(Category.Club)) } returns flowOf(persistentListOf())
                every { showResults("grill", persistentSetOf(Category.Club)) } returns
                    flowOf(persistentListOf())
                val presenter = SearchPresenter(showResults)
                val events = MutableSharedFlow<SearchEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        events.emit(SearchEvent.Search("grill"))
                        events.emit(SearchEvent.SetExpanded(true))
                        events.emit(SearchEvent.OpenResult)
                        events.emit(SearchEvent.Search(""))
                        events.emit(SearchEvent.SetExpanded(false))
                        runCurrent()

                        val afterTeardown = expectMostRecentItem() as SearchModel.Data
                        afterTeardown.query shouldBe "grill"
                        afterTeardown.expanded shouldBe true

                        events.emit(SearchEvent.UiReady)
                        events.emit(SearchEvent.SetExpanded(false))
                        runCurrent()

                        val afterDismiss = expectMostRecentItem() as SearchModel.Data
                        afterDismiss.query shouldBe "grill"
                        afterDismiss.expanded shouldBe false

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }

            it("collapses and clears the query on Clear") {
                val showResults = mockk<ShowSearchResultsUseCase>()
                every { showResults("", persistentSetOf(Category.Club)) } returns flowOf(persistentListOf())
                every { showResults("grill", persistentSetOf(Category.Club)) } returns
                    flowOf(persistentListOf())
                val presenter = SearchPresenter(showResults)
                val events = MutableSharedFlow<SearchEvent>(extraBufferCapacity = 20)

                runTest {
                    moleculeFlow(RecompositionMode.Immediate) { presenter.present(events) }.test {
                        events.emit(SearchEvent.Search("grill"))
                        events.emit(SearchEvent.SetExpanded(true))
                        runCurrent()
                        events.emit(SearchEvent.Clear)
                        runCurrent()

                        val model = expectMostRecentItem() as SearchModel.Data
                        model.query shouldBe ""
                        model.expanded shouldBe false

                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    })
