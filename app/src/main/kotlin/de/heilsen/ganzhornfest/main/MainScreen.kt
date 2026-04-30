package de.heilsen.ganzhornfest.main

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import de.heilsen.ganzhornfest.R
import de.heilsen.ganzhornfest.bus.BusScreen
import de.heilsen.ganzhornfest.bus.BusViewModel
import de.heilsen.ganzhornfest.countdown.CountdownScreen
import de.heilsen.ganzhornfest.countdown.CountdownViewModel
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
import de.heilsen.ganzhornfest.search.SearchScreen
import de.heilsen.ganzhornfest.search.SearchViewModel
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

interface EntryPoint {
    val busViewModel: BusViewModel
    val programViewModel: ProgramViewModel
    val mapViewModel: MapViewModel
    val searchViewModel: SearchViewModel
    val detailViewModel: DetailViewModel
    val countdownViewModel: CountdownViewModel
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hasRoute<Destination.Home>() == false
        }
    }
    val showSearchFab by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hasRoute<Destination.Search>() == false &&
                navBackStackEntry?.destination?.hasRoute<Destination.Home>() == false
        }
    }

    val entryPoint: EntryPoint by rememberAppGraph()
    val busViewModel: BusViewModel = entryPoint.busViewModel
    val programViewModel: ProgramViewModel = entryPoint.programViewModel
    val mapViewModel: MapViewModel = entryPoint.mapViewModel
    val searchViewModel: SearchViewModel = entryPoint.searchViewModel
    val detailViewModel: DetailViewModel = entryPoint.detailViewModel
    val countdownViewModel: CountdownViewModel = entryPoint.countdownViewModel

    GanzhornfestTheme {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    NavigationBar {
                        NavigationBarItem(
                            currentDestination?.hasRoute<Destination.Info>() ?: false,
                            icon = {
                                Icon(Icons.Default.Info, stringResource(R.string.info))
                            },
                            onClick = { navController.navigate(Destination.Info) },
                            label = { Text(stringResource(R.string.info)) })
                        NavigationBarItem(
                            currentDestination?.hasRoute<Destination.Map>() ?: false,
                            icon = {
                                Icon(Icons.Default.LocationOn, stringResource(R.string.map))
                            },
                            onClick = { navController.navigate(Destination.Map) },
                            label = { Text(stringResource(R.string.map)) })
                        NavigationBarItem(
                            currentDestination?.hasRoute<Destination.Program>() ?: false,
                            icon = {
                                Icon(Icons.Default.DateRange, stringResource(R.string.program))
                            },
                            onClick = { navController.navigate(Destination.Program) },
                            label = { Text(stringResource(R.string.program)) })
                        NavigationBarItem(
                            currentDestination?.hasRoute<Destination.Bus>() ?: false,
                            icon = {
                                Icon(
                                    ImageVector.vectorResource(id = de.heilsen.ganzhornfest.bus.api.R.drawable.ic_directions_bus_filled_24),
                                    stringResource(R.string.bustimes)
                                )
                            },
                            onClick = { navController.navigate(Destination.Bus) },
                            label = { Text(stringResource(R.string.bustimes)) })
                    }
                }
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = showSearchFab,
                    enter = fadeIn() + slideInHorizontally { it },
                    exit = fadeOut() + slideOutHorizontally { it }
                ) {
                    FloatingActionButton(onClick = { navController.navigate(Destination.Search) }) {
                        Icon(Icons.Default.Search, stringResource(R.string.search))
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destination.Home
            ) {
                composable<Destination.Home> {
                    val countdownModel by countdownViewModel.models.collectAsStateWithLifecycle()
                    CountdownScreen(
                        model = countdownModel,
                        onEnterApp = {
                            navController.navigate(Destination.Map) {
                                popUpTo(Destination.Home) { inclusive = true }
                            }
                        },
                    )
                }
                composable<Destination.Map> {
                    val mapModel by mapViewModel.models.collectAsStateWithLifecycle()
                    MapScreen(
                        modifier = Modifier.padding(innerPadding),
                        mapModel = mapModel,
                            onMarkerSelected = { title, type ->
                                when (type) {
                                    MarkerUiType.CLUB -> {
                                        navController.navigate(
                                            Destination.Detail(title, DetailType.Club)
                                        )
                                    }

                                    MarkerUiType.EVENT_LOCATION -> {
                                        navController.navigate(Destination.Program)
                                    }

                                    MarkerUiType.PLAYGROUND -> {
                                        navController.navigate(Destination.Program)
                                    }

                                    MarkerUiType.WC -> {
                                        // Nothing to see here
                                    }

                                    MarkerUiType.FIRST_AID -> {
                                        // Nothing to see here
                                    }

                                    MarkerUiType.BUS_STOP -> {
                                        navController.navigate(Destination.Bus)
                                    }
                                }
                            }
                        )
                }
                composable<Destination.Detail> { navBackStackEntry ->
                    val detail: Destination.Detail = navBackStackEntry.toRoute()

                    val detailEvent: DetailEvent = when (detail.type) {
                        DetailType.Club -> DetailEvent.Club(detail.title)
                        DetailType.Offer -> DetailEvent.Offer(detail.title)
                    }
                    detailViewModel.take(detailEvent)
                    val model by detailViewModel.models.collectAsStateWithLifecycle()
                    DetailScreen(
                        modifier = Modifier.padding(innerPadding),
                        model = model,
                        onBackClick = { navController.popBackStack() },
                        onItemClicked = { searchTerm, type ->
                            navController.navigate(
                                Destination.Detail(searchTerm, type)
                            )
                        }
                    )
                }
                composable<Destination.Program> {
                    val programModel by programViewModel.models.collectAsStateWithLifecycle()
                    ProgramScreen(
                        programModel,
                        onEvent = programViewModel::take,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
                composable<Destination.Info> {
                    InfoScreen(modifier = Modifier.padding(innerPadding))
                }
                composable<Destination.Bus> {
                    val busModel by busViewModel.models.collectAsStateWithLifecycle()
                    BusScreen(
                        busModel,
                        onEvent = busViewModel::take,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
                composable<Destination.Search> {
                    val searchModel by searchViewModel.models.collectAsStateWithLifecycle()
                    SearchScreen(
                        searchModel = searchModel,
                        onEvent = { searchViewModel.take(it) },
                        onSearchResultClicked = { item, category ->
                            //TODO: move navigation into viewmodel
                            val type = when (category) {
                                Category.Food,
                                Category.Drink -> DetailType.Offer

                                Category.Club -> DetailType.Club
                            }
                            navController.navigate(
                                Destination.Detail(item, type)
                            )
                        },
                        onBackPressed = { navController.popBackStack() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
