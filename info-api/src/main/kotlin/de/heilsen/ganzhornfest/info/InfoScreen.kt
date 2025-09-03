package de.heilsen.ganzhornfest.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import de.heilsen.ganzhornfest.datetime.AndroidDateTimeFormatter.localizedDateFormat
import de.heilsen.ganzhornfest.info.api.R
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import java.text.DateFormat
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    val isExpanded = topAppBarState.collapsedFraction < 0.5f
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            style = MaterialTheme.typography.headlineLarge,
                            text = stringResource(R.string.ganzhornfest_with_year),
                        )
                        if (isExpanded) {
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                text = stringResource(R.string.ganzhornfest_official_name),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 12.dp)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
            }
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    val context = LocalContext.current
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = localizedDateFormat(
                            context,
                            DateFormat.FULL,
                            stringResource(R.string.date_of_first_day)
                        )
                    )
                    Text(
                        text = stringResource(R.string.opening_hours_saturday)
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = localizedDateFormat(
                            context, DateFormat.FULL, stringResource(R.string.date_of_second_day)
                        )
                    )
                    Text(
                        text = stringResource(R.string.opening_hours_sunday)
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = localizedDateFormat(
                            context,
                            DateFormat.FULL,
                            stringResource(R.string.date_of_third_day)
                        )
                    )
                    Text(
                        text = stringResource(R.string.opening_hours_monday)
                    )
                }
            }

            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
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
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = buildAnnotatedString {
                        append("39 Neckarsulmer Vereine bieten:"); appendLine()
                        append("${Typography.bullet}\t\tinternationale und lokale ");
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Speisen")
                        }
                        appendLine()
                        append("${Typography.bullet}\t\tvielfältige ");
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Getränke")
                        }
                        append("auswahl")
                        appendLine()
                        append("${Typography.bullet}\t\tkünstlerische ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Veranstaltungen")
                        }
                        appendLine()
                        append("${Typography.bullet}\t\t")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Programmpunkte")
                        }
                        append(" für Kinder/Jugendliche")
                    }
                )
            }

            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
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
                        })
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
                    modifier = Modifier.padding(16.dp),
                    text = buildAnnotatedString {
                        append("Offizielle Informationen auf: ")
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
                    }
                )
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