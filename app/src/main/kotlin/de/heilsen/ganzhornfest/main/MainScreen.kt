package de.heilsen.ganzhornfest.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.heilsen.ganzhornfest.BuildConfig
import de.heilsen.ganzhornfest.R
import de.heilsen.ganzhornfest.bus.BusScreen
import de.heilsen.ganzhornfest.bus.BusViewModel
import de.heilsen.ganzhornfest.detail.DetailEvent
import de.heilsen.ganzhornfest.detail.DetailScreen
import de.heilsen.ganzhornfest.detail.DetailType
import de.heilsen.ganzhornfest.detail.DetailViewModel
import de.heilsen.ganzhornfest.di.getValue
import de.heilsen.ganzhornfest.di.rememberAppGraph
import de.heilsen.ganzhornfest.info.InfoScreen
import de.heilsen.ganzhornfest.map.MapScreen
import de.heilsen.ganzhornfest.map.MapViewModel
import de.heilsen.ganzhornfest.map.MarkerUiType
import de.heilsen.ganzhornfest.navigation.Destination
import de.heilsen.ganzhornfest.program.ProgramScreen
import de.heilsen.ganzhornfest.program.ProgramViewModel
import de.heilsen.ganzhornfest.search.Category
import de.heilsen.ganzhornfest.search.MapSearchBar
import de.heilsen.ganzhornfest.search.SearchViewModel
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

interface EntryPoint {
    val busViewModel: BusViewModel
    val programViewModel: ProgramViewModel
    val mapViewModel: MapViewModel
    val searchViewModel: SearchViewModel
    val detailViewModel: DetailViewModel
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val entryPoint: EntryPoint by rememberAppGraph()
    val busViewModel: BusViewModel = entryPoint.busViewModel
    val programViewModel: ProgramViewModel = entryPoint.programViewModel
    val mapViewModel: MapViewModel = entryPoint.mapViewModel
    // Unscoped Metro injection. Remember so the search session survives Map leaving composition.
    val searchViewModel = remember { entryPoint.searchViewModel }
    val detailViewModel: DetailViewModel = entryPoint.detailViewModel

    GanzhornfestTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        currentDestination?.hasRoute<Destination.Info>() ?: false,
                        icon = {
                            Icon(Icons.Default.Info, stringResource(R.string.info))
                        },
                        onClick = {
                            navController.navigate(Destination.Info) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.info)) },
                    )
                    NavigationBarItem(
                        currentDestination?.hasRoute<Destination.Map>() ?: false,
                        icon = {
                            Icon(Icons.Default.LocationOn, stringResource(R.string.map))
                        },
                        onClick = {
                            navController.navigate(Destination.Map) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.map)) },
                    )
                    NavigationBarItem(
                        currentDestination?.hasRoute<Destination.Program>() ?: false,
                        icon = {
                            Icon(Icons.Default.DateRange, stringResource(R.string.program))
                        },
                        onClick = {
                            navController.navigate(Destination.Program) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.program)) },
                    )
                    NavigationBarItem(
                        currentDestination?.hasRoute<Destination.Bus>() ?: false,
                        icon = {
                            Icon(
                                ImageVector.vectorResource(id = de.heilsen.ganzhornfest.bus.api.R.drawable.ic_directions_bus_filled_24),
                                stringResource(R.string.bustimes),
                            )
                        },
                        onClick = {
                            navController.navigate(Destination.Bus) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.bustimes)) },
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destination.Map,
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
            ) {
                composable<Destination.Map> {
                    val mapModel by mapViewModel.models.collectAsStateWithLifecycle()
                    val searchModel by searchViewModel.models.collectAsStateWithLifecycle()
                    Box(Modifier.fillMaxSize()) {
                        MapScreen(
                            mapModel = mapModel,
                            onEvent = mapViewModel::onEvent,
                            showPinEditorToggle = BuildConfig.DEBUG,
                            onMarkerSelected = { title, type ->
                                when (type) {
                                    MarkerUiType.CLUB -> {
                                        navController.navigate(Destination.Detail(title, DetailType.Club))
                                    }
                                    MarkerUiType.EVENT_LOCATION -> {
                                        navController.navigate(Destination.Program)
                                    }
                                    MarkerUiType.PLAYGROUND -> {
                                        navController.navigate(Destination.Program)
                                    }
                                    MarkerUiType.ATTRACTION -> { /* No detail screen. Tombola is not a club menu. */ }
                                    MarkerUiType.WC -> { }
                                    MarkerUiType.FIRST_AID -> { }
                                    MarkerUiType.BUS_STOP -> {
                                        navController.navigate(Destination.Bus)
                                    }
                                }
                            },
                        )
                        MapSearchBar(
                            searchModel = searchModel,
                            onEvent = { searchViewModel.take(it) },
                            onSearchResultClicked = { item, category ->
                                // TODO: move navigation into viewmodel
                                val type =
                                    when (category) {
                                        Category.Food,
                                        Category.Drink,
                                        -> DetailType.Offer

                                        Category.Club -> DetailType.Club
                                    }
                                navController.navigate(
                                    Destination.Detail(item, type),
                                )
                            },
                            modifier = Modifier.align(Alignment.TopCenter),
                        )
                    }
                }
                composable<Destination.Detail> { navBackStackEntry ->
                    val detail: Destination.Detail = navBackStackEntry.toRoute()

                    val detailEvent: DetailEvent =
                        when (detail.type) {
                            DetailType.Club -> DetailEvent.Club(detail.title)
                            DetailType.Offer -> DetailEvent.Offer(detail.title)
                        }
                    detailViewModel.take(detailEvent)
                    val model by detailViewModel.models.collectAsStateWithLifecycle()
                    DetailScreen(
                        model = model,
                        onBackClick = { navController.popBackStack() },
                        onItemClicked = { searchTerm, type ->
                            navController.navigate(
                                Destination.Detail(searchTerm, type),
                            )
                        },
                    )
                }
                composable<Destination.Program> {
                    val programModel by programViewModel.models.collectAsStateWithLifecycle()
                    ProgramScreen(
                        programModel,
                        onEvent = programViewModel::take,
                    )
                }
                composable<Destination.Info> {
                    InfoScreen()
                }
                composable<Destination.Bus> {
                    val busModel by busViewModel.models.collectAsStateWithLifecycle()
                    BusScreen(
                        busModel,
                        onEvent = busViewModel::take,
                    )
                }
            }
        }
    }
}
