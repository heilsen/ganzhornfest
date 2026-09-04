package de.heilsen.ganzhornfest.theme.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestSans
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

@Composable
fun GanzhornfestScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit),
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            GanzhornfestTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
            )
        },
    ) { paddingValues ->
        ConstrainedContent(Modifier.padding(paddingValues)) {
            Column(content = content)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// No modifier slot on purpose. Callers style GanzhornfestScaffold, not the bar inside it, and
// topAppBarColors leads so a caller can override just the colours positionally.
@Suppress("ComposeModifierMissing", "ComposeParameterOrder")
fun GanzhornfestTopAppBar(
    topAppBarColors: TopAppBarColors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
        ),
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            CompositionLocalProvider(
                LocalTextStyle provides
                    MaterialTheme.typography.titleLarge.copy(
                        fontFamily = GanzhornfestSans,
                    ),
                content = title,
            )
        },
        colors = topAppBarColors,
        navigationIcon = navigationIcon,
    )
}

@PreviewDefault
@Composable
// A preview takes no modifier.
@Suppress("ComposeModifierMissing")
fun GanzhornfestScaffoldPreview() {
    GanzhornfestTheme {
        GanzhornfestScaffold(
            title = { Text("Title") },
            navigationIcon = {
                IconButton(onClick = { /*no-op*/ }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
                }
            },
        ) {
            Text("Content")
        }
    }
}
