package de.heilsen.ganzhornfest.main

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.squareup.anvil.annotations.ContributesTo
import de.heilsen.ganzhornfest.R
import de.heilsen.ganzhornfest.bus.BusScreen
import de.heilsen.ganzhornfest.bus.BusViewModel
import de.heilsen.ganzhornfest.detail.DetailEvent
import de.heilsen.ganzhornfest.detail.DetailScreen
import de.heilsen.ganzhornfest.detail.DetailViewModel
import de.heilsen.ganzhornfest.detail.OfferTypeUi
import de.heilsen.ganzhornfest.di.AppScope
import de.heilsen.ganzhornfest.di.getValue
import de.heilsen.ganzhornfest.di.rememberAppScope
import de.heilsen.ganzhornfest.info.InfoScreen
import de.heilsen.ganzhornfest.map.MapScreen
import de.heilsen.ganzhornfest.map.MapViewModel
import de.heilsen.ganzhornfest.map.MarkerUiType
import de.heilsen.ganzhornfest.navigation.Destination
import de.heilsen.ganzhornfest.program.ProgramScreen
import de.heilsen.ganzhornfest.program.ProgramViewModel
import de.heilsen.ganzhornfest.search.SearchScreen
import de.heilsen.ganzhornfest.search.SearchViewModel
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

@ContributesTo(AppScope::class)
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
    val currentDestination by remember {
        derivedStateOf { navBackStackEntry?.destination }
    }
    val showSearchFab by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hasRoute<Destination.Search>()?.not() ?: false
        }
    }
    val entryPoint: EntryPoint by rememberAppScope()
    val busViewModel: BusViewModel = entryPoint.busViewModel
    val programViewModel: ProgramViewModel = entryPoint.programViewModel
    val mapViewModel: MapViewModel = entryPoint.mapViewModel
    val searchViewModel: SearchViewModel = entryPoint.searchViewModel
    val detailViewModel: DetailViewModel = entryPoint.detailViewModel

    GanzhornfestTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(currentDestination?.hasRoute<Destination.Info>() ?: false,
                        icon = {
                            Icon(Icons.Default.Info, stringResource(R.string.info))
                        },
                        onClick = { navController.navigate(Destination.Info) },
                        label = { Text(stringResource(R.string.info)) })
                    NavigationBarItem(currentDestination?.hasRoute<Destination.Map>() ?: false,
                        icon = {
                            Icon(Icons.Default.LocationOn, stringResource(R.string.map))
                        },
                        onClick = { navController.navigate(Destination.Map) },
                        label = { Text(stringResource(R.string.map)) })
                    NavigationBarItem(currentDestination?.hasRoute<Destination.Program>() ?: false,
                        icon = {
                            Icon(Icons.Default.DateRange, stringResource(R.string.program))
                        },
                        onClick = { navController.navigate(Destination.Program) },
                        label = { Text(stringResource(R.string.program)) })
                    NavigationBarItem(currentDestination?.hasRoute<Destination.Bus>() ?: false,
                        icon = {
                            Icon(
                                ImageVector.vectorResource(id = de.heilsen.ganzhornfest.bus.R.drawable.ic_directions_bus_filled_24),
                                stringResource(R.string.bustimes)
                            )
                        },
                        onClick = { navController.navigate(Destination.Bus) },
                        label = { Text(stringResource(R.string.bustimes)) })
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
            Surface(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    //TODO: change to map when there's details
//                    startDestination = Destination.Map
                    startDestination = Destination.Info
                ) {
                    composable<Destination.Map> {
                        val mapModel by mapViewModel.models.collectAsStateWithLifecycle()
                        MapScreen(
                            mapModel = mapModel,
                            onEvent = mapViewModel::take,
                            onMarkerSelected = { title, type ->
                                //TODO: implement Details
//                                println("onMarkerSelected: $title (type: $type)")
//                                if (type == MarkerUiType.CLUB) {
//                                    navController.navigate(Destination.Detail(title, type.toString()))
//                                }
                            }
                        )
                    }
                    composable<Destination.Detail> { navBackStackEntry ->
                        println("navigate to detail")
                        val detail: Destination.Detail = navBackStackEntry.toRoute()
                        println("detail: $detail")

                        val detailEvent: DetailEvent = when (detail.type) {
                            "club" -> DetailEvent.Club(detail.title)
                            "food" -> DetailEvent.Offer(detail.title, OfferTypeUi.Food)
                            "drink" -> DetailEvent.Offer(detail.title, OfferTypeUi.Drink)
                            else -> {
                                Log.e("MainScreen", "got unknown detail type")
                                DetailEvent.Init
                            }
                        }
                        detailViewModel.take(detailEvent)
                        val model by detailViewModel.models.collectAsStateWithLifecycle()
                        DetailScreen(model)
                    }
                    composable<Destination.Program> {
                        val programModel by programViewModel.models.collectAsStateWithLifecycle()
                        ProgramScreen(programModel, onEvent = programViewModel::take)
                    }
                    composable<Destination.Info> {
                        InfoScreen()
                    }
                    composable<Destination.Bus> {
                        val busModel by busViewModel.models.collectAsStateWithLifecycle()
                        BusScreen(busModel, onEvent = busViewModel::take)
                    }
                    composable<Destination.Search> {
                        val searchModel by searchViewModel.models.collectAsStateWithLifecycle()
                        SearchScreen(
                            searchModel = searchModel,
                            onEvent = { searchViewModel.take(it) },
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}