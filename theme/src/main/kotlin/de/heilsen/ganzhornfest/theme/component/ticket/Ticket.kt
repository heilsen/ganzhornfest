package de.heilsen.ganzhornfest.theme.component.ticket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
    sideBar: @Composable (ColumnScope.() -> Unit) = {},
    description: @Composable (RowScope.() -> Unit) = {},
) {
    val outline = MaterialTheme.colorScheme.outline
    val darkTheme = isSystemInDarkTheme()
    Card(
        modifier = modifier,
        shape = TicketShape(),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        border =
            if (darkTheme) {
                BorderStroke(1.dp, outline.copy(alpha = 0.65f))
            } else {
                null
            },
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .border(BorderStroke(1.dp, outline)),
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(0.755f)
                        .border(BorderStroke(1.dp, outline)),
            ) {
                label()
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    content = header,
                )
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    content = description,
                )
            }
            Column(
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = sideBar,
            )
        }
    }
}
