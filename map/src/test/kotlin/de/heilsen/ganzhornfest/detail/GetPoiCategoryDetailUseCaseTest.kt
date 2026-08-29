package de.heilsen.ganzhornfest.detail

import app.cash.turbine.test
import com.google.android.gms.maps.model.LatLng
import de.heilsen.ganzhornfest.map.GetMarkersUseCase
import de.heilsen.ganzhornfest.map.MarkerUi
import de.heilsen.ganzhornfest.map.MarkerUiType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.flowOf

class GetPoiCategoryDetailUseCaseTest :
    StringSpec({
        "deduplicates markers that share a title, like a club with two stands" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi("DLRG", LatLng(1.0, 1.0), MarkerUiType.CLUB),
                        MarkerUi("DLRG", LatLng(2.0, 2.0), MarkerUiType.CLUB),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase("CLUB").test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Stand",
                        type = DetailType.PoiCategory,
                        items = listOf(DetailItem("DLRG")),
                    )
                awaitComplete()
            }
        }

        "lists each wc separately now that they carry street names" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi("WC (Urbanstraße)", LatLng(3.0, 3.0), MarkerUiType.WC),
                        MarkerUi("WC (Engelgasse)", LatLng(1.0, 1.0), MarkerUiType.WC),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase("WC").test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "WC",
                        type = DetailType.PoiCategory,
                        items = listOf(DetailItem("WC (Engelgasse)"), DetailItem("WC (Urbanstraße)")),
                    )
                awaitComplete()
            }
        }

        "sorts distinct pois of a category alphabetically" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi("Karussell", LatLng(1.0, 1.0), MarkerUiType.ATTRACTION),
                        MarkerUi("Blumentombola", LatLng(2.0, 2.0), MarkerUiType.ATTRACTION),
                        MarkerUi("Sängerbund", LatLng(3.0, 3.0), MarkerUiType.CLUB),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase("ATTRACTION").test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Attraktion",
                        type = DetailType.PoiCategory,
                        items = listOf(DetailItem("Blumentombola"), DetailItem("Karussell")),
                    )
                awaitComplete()
            }
        }

        "returns an empty list for an unknown type name" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(persistentSetOf(MarkerUi("WC", LatLng(1.0, 1.0), MarkerUiType.WC)))
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase("NOT_A_TYPE").test {
                awaitItem() shouldBe
                    DetailModel.Success(title = "", type = DetailType.PoiCategory, items = emptyList())
                awaitComplete()
            }
        }
    })
