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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.info.api.R
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import kotlin.text.Typography
import kotlin.text.appendLine

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Card(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineLarge,
                        text = stringResource(R.string.ganzhornfest_with_year)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.ganzhornfest_official_name),
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
                        style = MaterialTheme.typography.bodyLarge,
                        //TODO: only provide date and let kotlinx datetiem do the formatting
                        text = stringResource(R.string.date_of_saturday)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.opening_hours_saturday)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.date_of_sunday)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.opening_hours_sunday)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.date_of_monday)
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.opening_hours_monday)
                    )
                }
            }

            Card(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                            appendLine("Neckarsulm")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            appendLine("rund um das Deutschordensschloss,")
                            append("den umliegenden Gassen und dem Karlsplatz")
                        }
                    }
                )
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
                            append("${Typography.bullet}\t\teine Vielzahl internationaler und lokaler ");
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Speisen")
                            }
                            appendLine()
                            append("${Typography.bullet}\t\teine üppige ");
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Getränke")
                            }
                            append("auswahl")
                            appendLine()
                            append("${Typography.bullet}\t\tüber 20 künstlerische")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(" Veranstaltungen")
                            }
                            appendLine()
                            append("${Typography.bullet}\t\tund mehrere ")
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
                                append("An den Festtagen dürfen alle Busse in Neckarsulm kostenlos genutzt werden (gilt nicht für Rufauto-Fahrten). Dieser Service wird ermöglicht durch die Stadt Neckarsulm, den HNV und die Busunternehmen FMO, OVR und Zartmann. Die Fahrpläne befinden sich auf den Aushängen an den Haltestellen sowie unter ")
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