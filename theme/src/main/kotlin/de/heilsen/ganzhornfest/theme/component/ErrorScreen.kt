package de.heilsen.ganzhornfest.theme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.compose.preview.DefaultPreviews
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    message: String = "Daten konnten nicht geladen werden",
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = message,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = "Bitte die App neu starten",
            textAlign = TextAlign.Center,
        )
    }
}

@DefaultPreviews
@Composable
@Suppress("ComposeModifierMissing")
private fun ErrorScreenPreview() {
    GanzhornfestTheme { ErrorScreen() }
}
