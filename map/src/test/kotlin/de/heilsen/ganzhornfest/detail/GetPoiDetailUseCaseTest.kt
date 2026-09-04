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

class GetPoiDetailUseCaseTest :
    StringSpec({
        "returns a card for the poi's own category" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns
                flowOf(
                    persistentSetOf(
                        MarkerUi(1, "Toiletten Marktplatz", LatLng(1.0, 1.0), MarkerUiType.WC),
                        MarkerUi(2, "Sängerbund", LatLng(2.0, 2.0), MarkerUiType.CLUB),
                    ),
                )
            val useCase = GetPoiDetailUseCase(getMarkers)

            useCase(1).test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Toiletten Marktplatz",
                        target = DetailTarget.Poi(1),
                        items = listOf(DetailItem(name = "WC", target = DetailTarget.Category(MarkerUiType.WC))),
                    )
                awaitComplete()
            }
        }

        "returns no card when the id matches no marker" {
            val getMarkers = mockk<GetMarkersUseCase>()
            every { getMarkers() } returns flowOf(persistentSetOf())
            val useCase = GetPoiDetailUseCase(getMarkers)

            useCase(999).test {
                awaitItem() shouldBe
                    DetailModel.Success(title = "", target = DetailTarget.Poi(999), items = emptyList())
                awaitComplete()
            }
        }
    })
