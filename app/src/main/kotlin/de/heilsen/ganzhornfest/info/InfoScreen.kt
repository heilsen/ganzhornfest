package de.heilsen.ganzhornfest.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import kotlin.text.Typography.bullet

@Composable
fun InfoScreen() {
    Surface {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Card(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        text = "Ganzhornfest 2024"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        text = "43. Herbstfest der Neckarsulmer Vereine",
                        textAlign = TextAlign.Center
                    )
                }
            }
            Card(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = "Samstag, 31. August 2024"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "16:00 - 01:00 Uhr"
                    )
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = "Sonntag, 1. September 2024"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "11:00 - 00:00 Uhr"
                    )
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = "Montag, 2. September 2024"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "11:00 - 01:00 Uhr"
                    )
                }
            }

            Card(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(style = MaterialTheme.typography.labelLarge, text = "Neckarsulm")
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "rund um das Deutschordensschloss,"
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "den umliegenden Gassen und dem Karlsplatz"
                    )
                }
            }
            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                Card(
                    Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        buildAnnotatedString {
                            append("39 Neckarsulmer Vereine bieten:"); appendLine()
                            append("$bullet\t\teine Vielzahl internationaler und lokaler ");
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Speisen")
                            }
                            appendLine()
                            append("$bullet\t\teine üppige ");
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Getränke")
                            }
                            append("auswahl")
                            appendLine()
                            append("$bullet\t\tüber 20 künstlerische")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(" Veranstaltungen")
                            }
                            appendLine()
                            append("$bullet\t\tund mehrere ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Programmpunkte")
                            }
                            append(" für Kinder/Jugendliche")
                        },
                        Modifier.padding(8.dp)
                    )
                }

                Card(
                    Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text(
                            buildAnnotatedString {
                                append("An den Festtagen dürfen alle Busse in Neckarsulm kostenlos genutzt werden (gilt nicht für Rufauto-Fahrten). Dieser Service wird ermöglicht durch die Stadt Neckarsulm, den HNV und die Busunternehmen OVR, RBS und Zartmann. Die Fahrpläne befinden sich auf den Aushängen an den Haltestellen sowie unter ")
                                withLink(LinkAnnotation.Url("https://www.neckarsulmer-stadtbus.de")) {
                                    withStyle(
                                        SpanStyle(
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textDecoration = TextDecoration.Underline
                                        )
                                    ) {
                                        append("https://www.neckarsulmer-stadtbus.de")
                                    }
                                }
                                append(".")
                            }
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text("Eine Übersicht über die Busrückfahrten vom Ganzhornfest findest Du auch hier in der App.")
                    }
                }
                Card(
                    Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Weitere Informationen auf ")
                            withLink(LinkAnnotation.Url("https://www.ganzhornfest.com")) {
                                withStyle(
                                    SpanStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append("https://www.ganzhornfest.com")
                                }
                            }
                            append(".")
                        },
                        Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@PreviewDefault
@Composable
fun InfoScreenPreview() {
    GanzhornfestTheme {
        InfoScreen()
    }
}
