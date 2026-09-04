package de.heilsen.ganzhornfest.theme.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

// Text lines past roughly 600 dp get hard to track back to the next line, and a tablet has far
// more width than that. Cap and centre rather than stretch.
private val CONTENT_MAX_WIDTH = 600.dp

@Composable
fun ConstrainedContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Box(Modifier.widthIn(max = CONTENT_MAX_WIDTH).fillMaxHeight()) { content() }
    }
}

@PreviewDefault
@Composable
// A preview takes no modifier.
@Suppress("ComposeModifierMissing")
fun ConstrainedContentPreview() {
    GanzhornfestTheme {
        ConstrainedContent {
            Text("The quick brown fox jumps over the lazy dog. ".repeat(12))
        }
    }
}
