package de.heilsen.ganzhornfest.theme.component.ticket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    backgroundColor: Color = contentColorFor(MaterialTheme.colorScheme.surfaceVariant),
    label: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
    sideBar: @Composable (ColumnScope.() -> Unit) = {},
    description: @Composable (RowScope.() -> Unit) = {},
) {
    Card(
        modifier = modifier,
        shape = TicketShape(25f)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 20.dp)
                .border(BorderStroke(2.dp, backgroundColor))
        ) {
            Column(
                modifier = Modifier
                    .weight(0.755f)
                    .border(BorderStroke(2.dp, backgroundColor)),
            ) {
                label()
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    contentColor = MaterialTheme.colorScheme.surfaceVariant,
                    color = contentColorFor(MaterialTheme.colorScheme.surfaceVariant),
                    content = header
                )
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    content = description
                )
            }
            Column(
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = sideBar
            )
        }
    }
}