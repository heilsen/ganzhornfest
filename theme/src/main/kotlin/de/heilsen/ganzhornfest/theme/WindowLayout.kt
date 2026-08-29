package de.heilsen.ganzhornfest.theme

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

/**
 * True when a secondary pane belongs beside the content instead of under it.
 *
 * Wide windows, so tablets and unfolded foldables, have room for two panes. Short windows,
 * so phones in landscape, lack the height for a panel underneath. Only a narrow and tall
 * window, a phone held upright, stacks.
 */
@Composable
fun isSidePanelLayout(): Boolean {
    val sizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    return sizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ||
        !sizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
}
