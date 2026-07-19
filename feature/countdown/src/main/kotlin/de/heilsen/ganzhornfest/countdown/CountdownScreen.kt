package de.heilsen.ganzhornfest.countdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.FestivalEdition
import de.heilsen.ganzhornfest.countdown.R

@Composable
fun CountdownScreen(
    model: CountdownModel,
    onEnterApp: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (model) {
                is CountdownModel.Before -> BeforeContent(model, onEnterApp)
                CountdownModel.During -> MessageContent(stringResource(R.string.countdown_message_during), onEnterApp)
                CountdownModel.After -> MessageContent(stringResource(R.string.countdown_message_after), onEnterApp)
            }
        }
    }
}

@Composable
private fun BeforeContent(
    model: CountdownModel.Before,
    onEnterApp: () -> Unit,
) {
    Text(
        text = "${FestivalEdition.editionNumber}. Ganzhornfest ${FestivalEdition.year}",
        style = MaterialTheme.typography.headlineSmall,
    )
    Spacer(modifier = Modifier.height(32.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        CountdownUnit(model.days, stringResource(R.string.countdown_unit_days))
        CountdownUnit(model.hours, stringResource(R.string.countdown_unit_hours))
        CountdownUnit(model.minutes, stringResource(R.string.countdown_unit_minutes))
        CountdownUnit(model.seconds, stringResource(R.string.countdown_unit_seconds))
    }
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onEnterApp) {
        Text(stringResource(R.string.countdown_cta))
    }
}

@Composable
private fun CountdownUnit(
    value: Long,
    label: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun MessageContent(
    message: String,
    onEnterApp: () -> Unit,
) {
    Text(
        text = "${FestivalEdition.editionNumber}. Ganzhornfest ${FestivalEdition.year}",
        style = MaterialTheme.typography.headlineSmall,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = message,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onEnterApp) {
        Text(stringResource(R.string.countdown_cta))
    }
}
