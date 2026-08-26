package de.heilsen.ganzhornfest.map

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinEditorPanel(
    pinEditor: PinEditorModel,
    previewLatLng: LatLng?,
    onSelectPin: (ClubPin) -> Unit,
    onApply: (coordinateId: Long) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val selected = pinEditor.selected
    var menuExpanded by remember { mutableStateOf(false) }
    var showCopyHelp by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Fadenkreuz auf den Standort, dann Position übernehmen. Marker antippen geht auch.",
                style = MaterialTheme.typography.bodySmall,
            )
            ExposedDropdownMenuBox(
                expanded = menuExpanded,
                onExpandedChange = { menuExpanded = it },
            ) {
                OutlinedTextField(
                    modifier =
                        Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                    value = selected?.chipLabel.orEmpty(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Standort") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
                )
                ExposedDropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    pinEditor.pins.forEach { pin ->
                        DropdownMenuItem(
                            text = { Text(pin.chipLabel) },
                            onClick = {
                                onSelectPin(pin)
                                menuExpanded = false
                            },
                        )
                    }
                }
            }
            Text(
                text =
                    previewLatLng?.let { latLng ->
                        "Fadenkreuz  %.6f, %.6f".format(latLng.latitude, latLng.longitude)
                    } ?: "Keine Koordinate",
                style = MaterialTheme.typography.labelMedium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = selected?.coordinateId != null,
                    onClick = {
                        val coordinateId = selected?.coordinateId ?: return@Button
                        onApply(coordinateId)
                    },
                ) {
                    Text("Position übernehmen")
                }
                TextButton(
                    onClick = {
                        val sql = clubPinsToSql(pinEditor.pins)
                        val clipboard = context.getSystemService(ClipboardManager::class.java)
                        clipboard.setPrimaryClip(ClipData.newPlainText("coordinate SQL", sql))
                        showCopyHelp = true
                    },
                ) {
                    Text("SQL kopieren")
                }
            }
            TextButton(onClick = onClose) {
                Text("Fertig")
            }
        }
    }
    if (showCopyHelp) {
        AlertDialog(
            onDismissRequest = { showCopyHelp = false },
            title = { Text("Koordinaten kopiert") },
            text = {
                Text(
                    "Zwischenablage enthält INSERT/UPDATE-Vorlagen mit id, lat und lng.\n\n" +
                        "Trage die Werte in app/src/main/assets/festival/data.json " +
                        "unter coordinates ein.\n\n" +
                        "Erhöhe dataVersion in " +
                        "app/src/main/assets/festival/manifest.json.",
                )
            },
            confirmButton = {
                TextButton(onClick = { showCopyHelp = false }) {
                    Text("OK")
                }
            },
        )
    }
}
