package de.heilsen.ganzhornfest.program

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.datetime.formatToLocalDate
import de.heilsen.ganzhornfest.core.datetime.formatToLocalTime
import de.heilsen.ganzhornfest.theme.component.EmptyScreen
import de.heilsen.ganzhornfest.theme.component.GanzhornfestScaffold
import de.heilsen.ganzhornfest.theme.component.LoadingScreen
import de.heilsen.ganzhornfest.theme.component.SelectionCard
import de.heilsen.ganzhornfest.theme.component.SelectionConfig
import de.heilsen.ganzhornfest.theme.component.ticket.Ticket
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import java.text.DateFormat

@Composable
fun ProgramScreen(
    programModel: ProgramModel,
    onEvent: (ProgramEvent) -> Unit = {}
) {
    GanzhornfestScaffold(title = {
        Text(
            "Programmplan"
        )
    }) {
        Box {
            Column {
                when (programModel) {
                    ProgramModel.Loading -> LoadingScreen()
                    is ProgramModel.Data -> ProgramScreenSuccess(programModel, onEvent)
                }
            }
        }

    }
}

@Composable
fun ProgramScreenSuccess(
    programModel: ProgramModel.Data,
    onEvent: (ProgramEvent) -> Unit = {}
) {
    val locations: ImmutableList<String> = programModel.locations
    val selectedLocation: String? = programModel.selectedLocation
    val dates: ImmutableList<LocalDate> = programModel.dates
    val selectedDate: LocalDate = programModel.selectedDate

    Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp)) {
        val selectionConfigs: List<SelectionConfig<*>> = listOf(
            SelectionConfig(
                selectedItem = selectedLocation,
                selectedItemLabel = "Bühne",
                items = locations,
                onItemSelected = { onEvent(ProgramEvent.ChangeLocation(it)) }
            ),
            SelectionConfig(
                selectedItem = selectedDate,
                selectedItemLabel = "Tag",
                items = dates,
                onItemSelected = { onEvent(ProgramEvent.ChangeDate(it)) }
            ) { formatToLocalDate(it, DateFormat.FULL) }
        )
        SelectionCard(selectionConfigs)
        Spacer(Modifier.height(8.dp))
        Programs(programModel.programs)
    }
}

@Composable
private fun Programs(programs: List<Program>) {
    if (programs.isEmpty()) {
        EmptyScreen()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = programs.size,
                key = { index -> programs[index].key }
            ) { index ->
                val program = programs[index]
                Ticket(
                    label = {
                        Text(
                            text = program.stage,
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 4.dp
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    header = {
                        Text(
                            text = program.name,
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    sideBar = {
                        val eventTime = buildString {
                            append(formatToLocalTime(program.start, DateFormat.SHORT))
                            val endTime = program.end
                            if (endTime != null) {
                                appendLine()
                                appendLine("-")
                                append(formatToLocalTime(endTime, DateFormat.SHORT))
                            }
                        }
                        Text(text = eventTime, textAlign = TextAlign.Center)
                    },
                    description = {
                        val description = program.description ?: return@Ticket
                        Text(
                            text = description,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )

            }
        }
    }
}