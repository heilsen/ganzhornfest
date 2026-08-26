package de.heilsen.ganzhornfest.main

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.heilsen.ganzhornfest.BuildConfig
import de.heilsen.ganzhornfest.R
import de.heilsen.ganzhornfest.bus.BusScreen
import de.heilsen.ganzhornfest.bus.BusViewModel
import de.heilsen.ganzhornfest.countdown.CountdownScreen
import de.heilsen.ganzhornfest.countdown.CountdownViewModel
import de.heilsen.ganzhornfest.detail.DetailEvent
import de.heilsen.ganzhornfest.detail.DetailModel
import de.heilsen.ganzhornfest.detail.DetailScreen
import de.heilsen.ganzhornfest.detail.DetailType
import de.heilsen.ganzhornfest.detail.DetailViewModel
import de.heilsen.ganzhornfest.detail.highlightTitles
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
    val isMapSurface by remember {
        derivedStateOf {
            val dest = navBackStackEntry?.destination
            dest?.hasRoute<Destination.Map>() == true ||
                dest?.hasRoute<Destination.Detail>() == true
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
                            isMapSurface,
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
                }
            },
        ) { innerPadding ->
            Box(
                Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .fillMaxSize(),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Destination.Home,
                    modifier = Modifier.fillMaxSize(),
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
                        // UI lives in the map overlay. Keep this destination for back stack.
                    }
                    composable<Destination.Detail> { navBackStackEntry ->
                        val detail: Destination.Detail = navBackStackEntry.toRoute()
                        val detailEvent: DetailEvent =
                            when (detail.type) {
                                DetailType.Club -> DetailEvent.Club(detail.title)
                                DetailType.Offer -> DetailEvent.Offer(detail.title)
                            }
                        detailViewModel.take(detailEvent)
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

                if (isMapSurface) {
                    MapDetailOverlay(
                        mapViewModel = mapViewModel,
                        searchViewModel = searchViewModel,
                        detailViewModel = detailViewModel,
                        navController = navController,
                        isDetail = currentDestination?.hasRoute<Destination.Detail>() == true,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapDetailOverlay(
    mapViewModel: MapViewModel,
    searchViewModel: SearchViewModel,
    detailViewModel: DetailViewModel,
    navController: NavHostController,
    isDetail: Boolean,
) {
    val mapModel by mapViewModel.models.collectAsStateWithLifecycle()
    val searchModel by searchViewModel.models.collectAsStateWithLifecycle()
    val detailModel by detailViewModel.models.collectAsStateWithLifecycle()
    var lastSuccess by remember { mutableStateOf<DetailModel.Success?>(null) }
    val success = detailModel as? DetailModel.Success
    if (success != null) {
        lastSuccess = success
    }
    LaunchedEffect(isDetail) {
        if (!isDetail) {
            lastSuccess = null
        }
    }
    val shownSuccess = lastSuccess.takeIf { isDetail }
    val highlightTitles: Set<String>? = shownSuccess?.highlightTitles()

    val isDetailState = rememberUpdatedState(isDetail)
    val sheetState =
        rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = if (isDetail) SheetValue.PartiallyExpanded else SheetValue.Hidden,
            // rememberSaveable keys on this lambda. A new instance would reset the sheet.
            confirmValueChange =
                remember {
                    { value: SheetValue ->
                        if (value == SheetValue.Hidden && isDetailState.value) {
                            navController.popBackStack()
                        }
                        true
                    }
                },
        )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    LaunchedEffect(isDetail, (detailModel as? DetailModel.Success)?.title) {
        if (isDetail) {
            sheetState.partialExpand()
        } else {
            sheetState.hide()
        }
    }
    val sheetExpanded = sheetState.currentValue == SheetValue.Expanded

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 128.dp,
        sheetDragHandle = { BottomSheetDefaults.DragHandle() },
        sheetContent = {
            if (isDetail) {
                DetailScreen(
                    model = shownSuccess ?: detailModel,
                    onBackClick = { navController.popBackStack() },
                    onItemClicked = { searchTerm, type ->
                        navController.navigate(Destination.Detail(searchTerm, type))
                    },
                )
            }
        },
    ) { sheetPadding ->
        Box(Modifier.fillMaxSize()) {
            val mapBottomPadding = if (isDetail) 128.dp else 8.dp
            MapScreen(
                mapModel = mapModel,
                highlightedTitles = highlightTitles,
                onEvent = mapViewModel::onEvent,
                showPinEditorToggle = BuildConfig.DEBUG && !isDetail,
                mapBottomPadding = mapBottomPadding,
                onMarkerSelected = { title, type ->
                    when (type) {
                        MarkerUiType.CLUB -> {
                            navController.navigate(Destination.Detail(title, DetailType.Club))
                        }
                        MarkerUiType.EVENT_LOCATION,
                        MarkerUiType.PLAYGROUND,
                        -> navController.navigate(Destination.Program)
                        MarkerUiType.BUS_STOP -> navController.navigate(Destination.Bus)
                        MarkerUiType.ATTRACTION,
                        MarkerUiType.WC,
                        MarkerUiType.FIRST_AID,
                        -> { }
                    }
                },
            )
            if (!sheetExpanded) {
                MapSearchBar(
                    searchModel = searchModel,
                    onEvent = { searchViewModel.take(it) },
                    onSearchResultClicked = { item, category ->
                        val type =
                            when (category) {
                                Category.Food,
                                Category.Drink,
                                -> DetailType.Offer
                                Category.Club -> DetailType.Club
                            }
                        navController.navigate(Destination.Detail(item, type))
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}
