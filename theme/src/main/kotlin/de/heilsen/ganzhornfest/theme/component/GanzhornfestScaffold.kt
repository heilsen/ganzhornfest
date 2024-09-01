package de.heilsen.ganzhornfest.theme.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

@Composable
fun GanzhornfestScaffold(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit),
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            GanzhornfestTopAppBar(
                title = title,
                navigationIcon = navigationIcon
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GanzhornfestTopAppBar(
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = title,
        colors = topAppBarColors,
        navigationIcon = navigationIcon
    )
}

@PreviewDefault
@Composable
fun GanzhornfestScaffoldPreview() {
    GanzhornfestTheme {
        GanzhornfestScaffold(
            title = { Text("Title") },
            navigationIcon = { IconButton(onClick = { /*no-op*/ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück") }
            }
        ) {
            Text("Content")
        }
    }
}