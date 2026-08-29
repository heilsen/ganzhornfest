package de.heilsen.ganzhornfest.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import de.heilsen.ganzhornfest.poi.PoiRepository
import dev.zacsweers.metro.Inject

class InfoPresenter
    @Inject
    constructor(
        private val poiRepository: PoiRepository,
    ) {
        @Composable
        fun present(): InfoModel {
            val clubCount by remember { poiRepository.countClubs() }.collectAsState(initial = null)
            return InfoModel(clubCount = clubCount?.toInt())
        }
    }
