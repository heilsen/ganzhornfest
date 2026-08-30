package de.heilsen.ganzhornfest.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
import de.heilsen.ganzhornfest.detail.DetailEvent
import de.heilsen.ganzhornfest.detail.DetailModel
import de.heilsen.ganzhornfest.detail.DetailScreen
import de.heilsen.ganzhornfest.detail.DetailType
import de.heilsen.ganzhornfest.detail.DetailViewModel
import de.heilsen.ganzhornfest.detail.highlightTitles
import de.heilsen.ganzhornfest.di.getValue
import de.heilsen.ganzhornfest.di.rememberAppGraph
import de.heilsen.ganzhornfest.info.InfoScreen
import de.heilsen.ganzhornfest.info.InfoViewModel
import de.heilsen.ganzhornfest.map.MapModel
import de.heilsen.ganzhornfest.map.MapScreen
import de.heilsen.ganzhornfest.map.MapViewModel
import de.heilsen.ganzhornfest.map.MarkerUiType
import de.heilsen.ganzhornfest.navigation.Destination
import de.heilsen.ganzhornfest.program.ProgramEvent
import de.heilsen.ganzhornfest.program.ProgramScreen
import de.heilsen.ganzhornfest.program.ProgramViewModel
import de.heilsen.ganzhornfest.search.Category
import de.heilsen.ganzhornfest.search.MapSearchBar
import de.heilsen.ganzhornfest.search.SearchModel
import de.heilsen.ganzhornfest.search.SearchViewModel
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import de.heilsen.ganzhornfest.theme.isSidePanelLayout

// Wide enough for an offer or club list without crowding the map beside it.
private val DETAIL_PANE_WIDTH = 360.dp

interface EntryPoint {
    val busViewModel: BusViewModel
    val programViewModel: ProgramViewModel
    val mapViewModel: MapViewModel
    val searchViewModel: SearchViewModel
    val detailViewModel: DetailViewModel
    val infoViewModel: InfoViewModel
}

// EntryPoint's ViewModels are unscoped in the DI graph, so reading them off the
// interface builds a fresh instance every time. Reading all six once into this
// holder, then remembering the holder, keeps MainScreen from recreating them (and
// restarting their underlying molecule flows) on every navigation.
private class MainViewModels(
    entryPoint: EntryPoint,
) {
    val busViewModel: BusViewModel = entryPoint.busViewModel
    val programViewModel: ProgramViewModel = entryPoint.programViewModel
    val mapViewModel: MapViewModel = entryPoint.mapViewModel
    val searchViewModel: SearchViewModel = entryPoint.searchViewModel
    val detailViewModel: DetailViewModel = entryPoint.detailViewModel
    val infoViewModel: InfoViewModel = entryPoint.infoViewModel
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isMapSurface by remember {
        derivedStateOf {
            val dest = navBackStackEntry?.destination
            dest?.hasRoute<Destination.Map>() == true ||
                dest?.hasRoute<Destination.Detail>() == true
        }
    }

    val entryPoint: EntryPoint by rememberAppGraph()
    val viewModels = remember(entryPoint) { MainViewModels(entryPoint) }
    val busViewModel = viewModels.busViewModel
    val programViewModel = viewModels.programViewModel
    val mapViewModel = viewModels.mapViewModel
    val searchViewModel = viewModels.searchViewModel
    val detailViewModel = viewModels.detailViewModel
    val infoViewModel = viewModels.infoViewModel

    GanzhornfestTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    tonalElevation = 0.dp,
                ) {
                    val wine = MaterialTheme.colorScheme.primary
                    val itemColors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor = wine,
                            selectedTextColor = wine,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        )
                    val infoSelected = currentDestination?.hasRoute<Destination.Info>() ?: false
                    NavigationBarItem(
                        infoSelected,
                        icon = {
                            Icon(
                                imageVector = if (infoSelected) Icons.Filled.Info else Icons.Outlined.Info,
                                contentDescription = stringResource(R.string.info),
                            )
                        },
                        onClick = {
                            navController.navigate(Destination.Info) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.info)) },
                        colors = itemColors,
                    )
                    NavigationBarItem(
                        isMapSurface,
                        icon = {
                            Icon(
                                imageVector = if (isMapSurface) Icons.Filled.LocationOn else Icons.Outlined.LocationOn,
                                contentDescription = stringResource(R.string.map),
                            )
                        },
                        onClick = {
                            // Detail sits on the map surface, so this tab is already
                            // selected while a detail is open. Tapping it means "back to
                            // the plain map". Restoring state here would put the saved
                            // detail, or whatever was pushed on top of it, straight back.
                            navController.navigate(Destination.Map) {
                                popUpTo(Destination.Map)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(stringResource(R.string.map)) },
                        colors = itemColors,
                    )
                    val programSelected = currentDestination?.hasRoute<Destination.Program>() ?: false
                    NavigationBarItem(
                        programSelected,
                        icon = {
                            Icon(
                                imageVector = if (programSelected) Icons.Filled.DateRange else Icons.Outlined.DateRange,
                                contentDescription = stringResource(R.string.program),
                            )
                        },
                        onClick = {
                            navController.navigate(Destination.Program()) {
                                popUpTo(Destination.Map) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(R.string.program)) },
                        colors = itemColors,
                    )
                    val busSelected = currentDestination?.hasRoute<Destination.Bus>() ?: false
                    NavigationBarItem(
                        busSelected,
                        icon = {
                            Icon(
                                imageVector = if (busSelected) Icons.Filled.DirectionsBus else Icons.Outlined.DirectionsBus,
                                contentDescription = stringResource(R.string.bustimes),
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
                        colors = itemColors,
                    )
                }
            },
        ) { innerPadding ->
            // Each route applies innerPadding to its own root instead of one shared modifier:
            // a value gated on the current destination flips the instant navigate() commits,
            // before the outgoing screen's cross-fade finishes, so a shared modifier jumped
            // under the status bar mid-transition.
            val layoutDirection = LocalLayoutDirection.current
            Box(Modifier.fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = Destination.Map,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    composable<Destination.Map> {
                        // UI lives in the map overlay. Keep this destination for back stack.
                    }
                    composable<Destination.Detail> { navBackStackEntry ->
                        val detail: Destination.Detail = navBackStackEntry.toRoute()
                        val detailEvent: DetailEvent =
                            when (detail.type) {
                                DetailType.Club -> DetailEvent.Club(detail.title)
                                DetailType.Offer -> DetailEvent.Offer(detail.title)
                                DetailType.Poi -> DetailEvent.Poi(detail.title)
                                DetailType.PoiCategory -> DetailEvent.PoiCategory(detail.title)
                            }
                        LaunchedEffect(detailViewModel, detailEvent) {
                            detailViewModel.take(detailEvent)
                        }
                    }
                    composable<Destination.Program> { navBackStackEntry ->
                        val route: Destination.Program = navBackStackEntry.toRoute()
                        LaunchedEffect(programViewModel, route.stage) {
                            route.stage?.let { programViewModel.take(ProgramEvent.ChangeLocation(it)) }
                        }
                        val programModel by programViewModel.models.collectAsStateWithLifecycle()
                        ProgramScreen(
                            programModel,
                            onEvent = programViewModel::take,
                            modifier =
                                Modifier
                                    .padding(innerPadding)
                                    .consumeWindowInsets(innerPadding),
                        )
                    }
                    composable<Destination.Info> {
                        val infoModel by infoViewModel.models.collectAsStateWithLifecycle()
                        // Info manages its own top inset with the collapsing hero app bar, so it
                        // only takes the start/end/bottom portion of innerPadding here.
                        val infoPadding =
                            PaddingValues(
                                start = innerPadding.calculateStartPadding(layoutDirection),
                                top = 0.dp,
                                end = innerPadding.calculateEndPadding(layoutDirection),
                                bottom = innerPadding.calculateBottomPadding(),
                            )
                        InfoScreen(
                            clubCount = infoModel.clubCount,
                            modifier =
                                Modifier
                                    .padding(infoPadding)
                                    .consumeWindowInsets(infoPadding),
                        )
                    }
                    composable<Destination.Bus> {
                        val busModel by busViewModel.models.collectAsStateWithLifecycle()
                        BusScreen(
                            busModel,
                            onEvent = busViewModel::take,
                            modifier =
                                Modifier
                                    .padding(innerPadding)
                                    .consumeWindowInsets(innerPadding),
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
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding),
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
    modifier: Modifier = Modifier,
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

    val sidePanel = isSidePanelLayout()

    BoxWithConstraints(modifier.fillMaxSize()) {
        // Keep the map at least half the width on a narrow two pane window.
        val paneWidth = minOf(DETAIL_PANE_WIDTH, maxWidth / 2)
        if (sidePanel) {
            Row(Modifier.fillMaxSize()) {
                MapPane(
                    mapModel = mapModel,
                    searchModel = searchModel,
                    highlightTitles = highlightTitles,
                    isDetail = isDetail,
                    showSearchBar = true,
                    mapBottomPadding = 8.dp,
                    mapViewModel = mapViewModel,
                    searchViewModel = searchViewModel,
                    navController = navController,
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                )
                if (isDetail) {
                    Surface(
                        modifier = Modifier.width(paneWidth).fillMaxHeight(),
                        tonalElevation = 1.dp,
                    ) {
                        DetailScreen(
                            model = shownSuccess ?: detailModel,
                            onBackClick = { navController.popBackStack() },
                            onItemClicked = { searchTerm, type ->
                                navController.navigate(Destination.Detail(searchTerm, type))
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        } else {
            DetailSheetLayout(
                halfScreen = maxHeight / 2,
                isDetail = isDetail,
                detailModel = detailModel,
                shownSuccess = shownSuccess,
                navController = navController,
            ) { mapBottomPadding, showSearchBar ->
                MapPane(
                    mapModel = mapModel,
                    searchModel = searchModel,
                    highlightTitles = highlightTitles,
                    isDetail = isDetail,
                    showSearchBar = showSearchBar,
                    mapBottomPadding = mapBottomPadding,
                    mapViewModel = mapViewModel,
                    searchViewModel = searchViewModel,
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailSheetLayout(
    halfScreen: Dp,
    isDetail: Boolean,
    detailModel: DetailModel,
    shownSuccess: DetailModel.Success?,
    navController: NavHostController,
    content: @Composable (mapBottomPadding: Dp, showSearchBar: Boolean) -> Unit,
) {
    val isDetailState = rememberUpdatedState(isDetail)
    val sheetState =
        rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = if (isDetail) SheetValue.PartiallyExpanded else SheetValue.Hidden,
            // rememberSaveable keys on this lambda. A new instance would reset the sheet.
            confirmValueChange = remember { { _: SheetValue -> true } },
        )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    LaunchedEffect(isDetail, (detailModel as? DetailModel.Success)?.title) {
        if (isDetail) {
            sheetState.partialExpand()
        } else {
            sheetState.hide()
        }
    }
    // Pop only once a drag settles on Hidden, not on every candidate confirmValueChange
    // considers while predicting where a fling will land.
    LaunchedEffect(Unit) {
        snapshotFlow { sheetState.currentValue }
            .collect { value ->
                if (value == SheetValue.Hidden && isDetailState.value) {
                    navController.popBackStack()
                }
            }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = halfScreen,
        sheetDragHandle = { BottomSheetDefaults.DragHandle() },
        sheetContent = {
            if (isDetail) {
                DetailScreen(
                    model = shownSuccess ?: detailModel,
                    onBackClick = { navController.popBackStack() },
                    onItemClicked = { searchTerm, type ->
                        navController.navigate(Destination.Detail(searchTerm, type))
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
    ) {
        content(
            if (isDetail) halfScreen else 8.dp,
            sheetState.currentValue != SheetValue.Expanded,
        )
    }
}

@Composable
private fun MapPane(
    mapModel: MapModel,
    searchModel: SearchModel,
    highlightTitles: Set<String>?,
    isDetail: Boolean,
    showSearchBar: Boolean,
    mapBottomPadding: Dp,
    mapViewModel: MapViewModel,
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    // The editor panel sits beside the map on a compact height window, where a search bar
    // spanning the whole pane would cover it. Search is meaningless mid-edit anyway.
    var pinEditorOpen by remember { mutableStateOf(false) }
    Box(modifier) {
        MapScreen(
            mapModel = mapModel,
            highlightedTitles = highlightTitles,
            onEvent = mapViewModel::onEvent,
            showPinEditorToggle = BuildConfig.DEBUG && !isDetail,
            mapBottomPadding = mapBottomPadding,
            pinEditorOpen = pinEditorOpen,
            onPinEditorOpenChange = { pinEditorOpen = it },
            onMarkerSelected = { title, type ->
                when (type) {
                    MarkerUiType.CLUB -> {
                        navController.navigate(Destination.Detail(title, DetailType.Club))
                    }
                    MarkerUiType.EVENT_LOCATION,
                    MarkerUiType.PLAYGROUND,
                    -> navController.navigate(Destination.Program(title))
                    MarkerUiType.BUS_STOP -> navController.navigate(Destination.Bus)
                    MarkerUiType.ATTRACTION,
                    MarkerUiType.WC,
                    MarkerUiType.FIRST_AID,
                    -> navController.navigate(Destination.Detail(title, DetailType.Poi))
                }
            },
        )
        if (showSearchBar && !pinEditorOpen) {
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
