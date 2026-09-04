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
        "deduplicates markers that share a poi id, like a club with two stands" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi(1, "DLRG", LatLng(1.0, 1.0), MarkerUiType.CLUB),
                        MarkerUi(1, "DLRG", LatLng(2.0, 2.0), MarkerUiType.CLUB),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase(MarkerUiType.CLUB).test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Stand",
                        target = DetailTarget.Category(MarkerUiType.CLUB),
                        items = listOf(DetailItem("DLRG", target = DetailTarget.Poi(1))),
                    )
                awaitComplete()
            }
        }

        "lists each wc separately now that they carry street names" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi(10, "WC (Urbanstraße)", LatLng(3.0, 3.0), MarkerUiType.WC),
                        MarkerUi(11, "WC (Engelgasse)", LatLng(1.0, 1.0), MarkerUiType.WC),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase(MarkerUiType.WC).test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "WC",
                        target = DetailTarget.Category(MarkerUiType.WC),
                        items =
                            listOf(
                                DetailItem("WC (Engelgasse)", target = DetailTarget.Poi(11)),
                                DetailItem("WC (Urbanstraße)", target = DetailTarget.Poi(10)),
                            ),
                    )
                awaitComplete()
            }
        }

        "sorts distinct pois of a category alphabetically" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi(20, "Karussell", LatLng(1.0, 1.0), MarkerUiType.ATTRACTION),
                        MarkerUi(21, "Blumentombola", LatLng(2.0, 2.0), MarkerUiType.ATTRACTION),
                        MarkerUi(22, "Sängerbund", LatLng(3.0, 3.0), MarkerUiType.CLUB),
                    ),
                )
            val useCase = GetPoiCategoryDetailUseCase(getMarkers)

            useCase(MarkerUiType.ATTRACTION).test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Attraktion",
                        target = DetailTarget.Category(MarkerUiType.ATTRACTION),
                        items =
                            listOf(
                                DetailItem("Blumentombola", target = DetailTarget.Poi(21)),
                                DetailItem("Karussell", target = DetailTarget.Poi(20)),
                            ),
                    )
                awaitComplete()
            }
        }
    })
