package de.heilsen.ganzhornfest.info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.FestivalEdition
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.core.datetime.formatToLocalWeekdayDate
import de.heilsen.ganzhornfest.info.api.R
import de.heilsen.ganzhornfest.theme.GanzhornfestSans
import de.heilsen.ganzhornfest.theme.GanzhornfestSerif
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    clubCount: Int?,
    modifier: Modifier = Modifier,
) {
    val today = remember { Clock.System.todayIn(TimeZone.currentSystemDefault()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val wine = MaterialTheme.colorScheme.primary
    val paper = MaterialTheme.colorScheme.surface
    val darkTheme = isSystemInDarkTheme()
    // Dark mode does not animate with the scroll, so it can be a plain remembered brush.
    // Light mode fades with collapsedFraction, so its brush is rebuilt in drawBehind below,
    // at draw time instead of composition time.
    val darkHeroOverlayBrush =
        remember(paper) {
            Brush.verticalGradient(
                0f to paper.copy(alpha = 0.7f),
                0.22f to paper.copy(alpha = 0.35f),
                0.55f to paper.copy(alpha = 0.2f),
                1f to paper,
            )
        }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box {
                Image(
                    painter = painterResource(R.drawable.info_hero_rathaus),
                    contentDescription = stringResource(R.string.info_hero_content_description),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier =
                        Modifier
                            .matchParentSize()
                            .graphicsLayer { alpha = 1f - scrollBehavior.state.collapsedFraction },
                )
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .let {
                                if (darkTheme) {
                                    it.background(darkHeroOverlayBrush)
                                } else {
                                    it.drawBehind {
                                        val collapsedFraction = scrollBehavior.state.collapsedFraction
                                        drawRect(
                                            brush =
                                                Brush.verticalGradient(
                                                    0f to Color.Transparent,
                                                    0.35f to Color.Transparent,
                                                    0.65f to paper.copy(alpha = 0.7f * (1f - collapsedFraction)),
                                                    1f to paper.copy(alpha = 1f - collapsedFraction),
                                                ),
                                        )
                                    }
                                }
                            },
                )
                LargeTopAppBar(
                    title = {
                        Column {
                            Text(
                                text =
                                    stringResource(
                                        R.string.ganzhornfest_with_year,
                                        FestivalEdition.year,
                                    ),
                                fontFamily = GanzhornfestSans,
                                fontWeight = FontWeight.SemiBold,
                            )
                            if (scrollBehavior.state.collapsedFraction < 0.5f) {
                                Text(
                                    text =
                                        stringResource(
                                            R.string.ganzhornfest_official_name,
                                            FestivalEdition.editionNumber,
                                        ),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontFamily = GanzhornfestSans,
                                )
                            }
                        }
                    },
                    expandedHeight = 220.dp,
                    colors =
                        TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = paper,
                            titleContentColor = wine,
                        ),
                    scrollBehavior = scrollBehavior,
                )
            }
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LocationCard()
            DateChipRow(today = today)
            SundayShoppingBanner()
            ClubsCard(clubCount = clubCount)
            BusCard()
            OfficialLinksCard()
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DateChipRow(
    today: LocalDate,
    modifier: Modifier = Modifier,
) {
    val hours =
        listOf(
            stringResource(R.string.opening_hours_saturday),
            stringResource(R.string.opening_hours_sunday),
            stringResource(R.string.opening_hours_monday),
        )
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // zip, not forEachIndexed + hours[index]: a day added to FestivalEdition.days without a
        // matching opening_hours_* string truncates to the shorter list instead of crashing.
        FestivalEdition.days.zip(hours).forEach { (date, hoursText) ->
            DateChip(
                date = date,
                hours = hoursText,
                highlighted = date == today,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DateChip(
    date: LocalDate,
    hours: String,
    highlighted: Boolean,
    modifier: Modifier = Modifier,
) {
    val wine = MaterialTheme.colorScheme.primary
    val ink = MaterialTheme.colorScheme.onSurface
    val container =
        if (highlighted) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
        }
    val border =
        if (highlighted) {
            BorderStroke(2.dp, wine)
        } else {
            BorderStroke(1.dp, wine.copy(alpha = 0.35f))
        }
    val todayDescription = stringResource(R.string.today)
    Surface(
        modifier =
            modifier.semantics(mergeDescendants = true) {
                if (highlighted) {
                    stateDescription = todayDescription
                }
            },
        shape = MaterialTheme.shapes.medium,
        color = container,
        contentColor = ink,
        border = border,
        shadowElevation = 1.dp,
    ) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
            Text(
                text = formatToLocalWeekdayDate(date),
                style = MaterialTheme.typography.titleMedium,
                color = wine,
            )
            Text(
                text = hours,
                style = MaterialTheme.typography.titleMedium,
                color = ink,
            )
        }
    }
}

@Composable
private fun LocationCard(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
        colors = infoCardColors(),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.location_title),
                modifier = Modifier.padding(bottom = 12.dp),
                style = infoCaptionStyle(),
            )
            Text(
                text = stringResource(R.string.location_body),
                style = infoNoteStyle(),
            )
        }
    }
}

@Composable
private fun SundayShoppingBanner(modifier: Modifier = Modifier) {
    Surface(
        modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
        shadowElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.sunday_shopping),
                style = infoNoteStyle(),
            )
        }
    }
}

@Composable
private fun ClubsCard(
    clubCount: Int?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier.fillMaxWidth(),
        colors = infoCardColors(),
    ) {
        Column(Modifier.padding(16.dp)) {
            // Skip the headline while clubCount is still loading, instead of flashing an
            // incorrect "0 Neckarsulmer Vereine" until the DB flow's first emission arrives.
            if (clubCount != null) {
                Text(
                    text = pluralStringResource(R.plurals.clubs_intro, clubCount, clubCount),
                    modifier = Modifier.padding(bottom = 12.dp),
                    style = infoCaptionStyle(),
                )
            }
            Text(
                style = infoNoteStyle(),
                text =
                    buildAnnotatedString {
                        append("${Typography.bullet}\t\tinternationale und lokale ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Speisen")
                        }
                        appendLine()
                        append("${Typography.bullet}\t\tvielfältige ")
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
                    },
            )
        }
    }
}

@Composable
private fun BusCard(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
        colors = infoCardColors(),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.bus_title),
                modifier = Modifier.padding(bottom = 12.dp),
                style = infoCaptionStyle(),
            )
            Text(
                style = infoNoteStyle(),
                text =
                    buildAnnotatedString {
                        append(
                            "An den Festtagen dürfen alle Busse in Neckarsulm kostenlos genutzt werden (gilt nicht für Rufauto-Fahrten). Dieser Service wird ermöglicht durch die Stadt Neckarsulm, den HNV und die Busunternehmen FMO, OVR und Zartmann. Die Fahrpläne befinden sich auf den Aushängen an den Haltestellen sowie unter ",
                        )
                        withLink(LinkAnnotation.Url("https://www.neckarsulmer-stadtbus.de")) {
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textDecoration = TextDecoration.Underline,
                                ),
                            ) {
                                append("https://www.neckarsulmer-stadtbus.de")
                            }
                        }
                        append(".")
                    },
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Eine Übersicht über die Busrückfahrten vom Ganzhornfest findest Du auch hier in der App.",
                style = infoNoteStyle(),
            )
        }
    }
}

@Composable
private fun OfficialLinksCard(modifier: Modifier = Modifier) {
    val linkStyle =
        SpanStyle(
            color = MaterialTheme.colorScheme.onSurface,
            textDecoration = TextDecoration.Underline,
        )
    val websiteLabel = stringResource(R.string.official_website_label)
    val website = stringResource(R.string.official_website)
    val websiteUrl = stringResource(R.string.official_website_url)
    val instagramLabel = stringResource(R.string.instagram)
    val facebookLabel = stringResource(R.string.facebook)
    val profile = stringResource(R.string.profile)
    val hashtag = stringResource(R.string.hashtag)
    val instagramUrl = stringResource(R.string.instagram_url)
    val instagramHashtagUrl = stringResource(R.string.instagram_hashtag_url)
    val facebookUrl = stringResource(R.string.facebook_url)
    val facebookHashtagUrl = stringResource(R.string.facebook_hashtag_url)
    val fontsLabel = stringResource(R.string.fonts_label)
    val sourceSans3 = stringResource(R.string.fonts_source_sans_3)
    val sourceSans3Url = stringResource(R.string.fonts_source_sans_3_url)
    val fraunces = stringResource(R.string.fonts_fraunces)
    val frauncesUrl = stringResource(R.string.fonts_fraunces_url)
    Card(
        modifier.fillMaxWidth(),
        colors = infoCardColors(),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.official_info_title),
                modifier = Modifier.padding(bottom = 12.dp),
                style = infoCaptionStyle(),
            )
            Text(
                style = infoNoteStyle(),
                text =
                    officialInfoItem(websiteLabel) {
                        withLink(LinkAnnotation.Url(websiteUrl)) {
                            withStyle(linkStyle) { append(website) }
                        }
                    },
            )
            Text(
                style = infoNoteStyle(),
                text =
                    officialInfoItem(instagramLabel) {
                        withLink(LinkAnnotation.Url(instagramUrl)) {
                            withStyle(linkStyle) { append(profile) }
                        }
                        append("  ")
                        withLink(LinkAnnotation.Url(instagramHashtagUrl)) {
                            withStyle(linkStyle) { append(hashtag) }
                        }
                    },
            )
            Text(
                style = infoNoteStyle(),
                text =
                    officialInfoItem(facebookLabel) {
                        withLink(LinkAnnotation.Url(facebookUrl)) {
                            withStyle(linkStyle) { append(profile) }
                        }
                        append("  ")
                        withLink(LinkAnnotation.Url(facebookHashtagUrl)) {
                            withStyle(linkStyle) { append(hashtag) }
                        }
                    },
            )
            Text(
                style = infoNoteStyle(),
                text =
                    officialInfoItem(fontsLabel) {
                        withLink(LinkAnnotation.Url(sourceSans3Url)) {
                            withStyle(linkStyle) { append(sourceSans3) }
                        }
                        append(", ")
                        withLink(LinkAnnotation.Url(frauncesUrl)) {
                            withStyle(linkStyle) { append(fraunces) }
                        }
                    },
            )
        }
    }
}

@Composable
private fun infoCardColors() =
    CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

@Composable
private fun infoCaptionStyle() = MaterialTheme.typography.titleSmall.copy(fontFamily = GanzhornfestSerif)

@Composable
private fun infoNoteStyle() = MaterialTheme.typography.bodyMedium.copy(fontFamily = GanzhornfestSans)

private fun officialInfoItem(
    label: String,
    links: AnnotatedString.Builder.() -> Unit,
) = buildAnnotatedString {
    append("${Typography.bullet}\t\t")
    append(label)
    append(": ")
    links()
}

@PreviewDefault
@Composable
fun InfoScreenPreview() {
    GanzhornfestTheme {
        InfoScreen(clubCount = 34)
    }
}
