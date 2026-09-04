# P8 · Nav IDs and scoped ViewModels — Wave 2, blocked

## ⚠ Blocked

`feat/festival-data-2026` currently has uncommitted changes to `MainScreen.kt`, all of `:map`,
and several `.sq` files. This plan's step 1 rewrites `Destination.Detail` and every call site
that constructs it — several of which live in files that branch is actively editing
(`MainScreen.kt`'s `onMarkerSelected`/`onSearchResultClicked` lambdas). **Do not start this
worktree until `feat/festival-data-2026` has merged to `main`.** Once merged, re-read
`MainScreen.kt`, `:map`, and `Destination.kt` fresh before starting — this plan's line
references below are against pre-merge `main` and will drift.

## Goal

Three related fixes, all converging on the same root cause: navigation and view-model lifetime
are not modeled correctly.

1. Route to detail screens by stable ID, not display title.
2. Fire navigation-triggering events from a side effect, not inline during composition.
3. Scope ViewModels to their `NavBackStackEntry` instead of holding them as permanent
   `AppScope` singletons that never get `onCleared()`.

## Why now

**(a) Title-based routing.** `app/src/main/kotlin/de/heilsen/ganzhornfest/navigation/Destination.kt:26-29`:
```kotlin
data class Detail(
    val title: String,
    val type: DetailType,
) : Destination
```
Every call site — `MainScreen.kt:186` (map marker tap), `MainScreen.kt:227` (detail-to-detail
navigation), `MainScreen.kt:266` (search result tap) — passes a display **name** as the
navigation key. Downstream, `GetClubDetailUseCase.kt:17` and the repository layer resolve that
name back to a row via `LIKE '%name%'` (`ClubOffer.sq:264-268`,
`PoiRepository.selectByName`). Two clubs with overlapping substrings collide; a club rename
between festival editions silently breaks any saved/deep-linked route; there is no way to
deep-link to a specific club at all, since the "ID" the app uses is unstable, human-readable
text. `docs/TODO.md`'s top priority item is exactly this: "Replace title-based detail routing
with stable IDs."

**(b) Navigation triggered inline during composition.** `MainScreen.kt:213-221`:
```kotlin
composable<Destination.Detail> { navBackStackEntry ->
    val detail: Destination.Detail = navBackStackEntry.toRoute()
    val detailEvent: DetailEvent = when (detail.type) { ... }
    detailViewModel.take(detailEvent)   // <-- called directly in the composable body
    val model by detailViewModel.models.collectAsState(...)
```
`detailViewModel.take(detailEvent)` runs on every recomposition of this composable, not once per
navigation. It happens to work because `take()` is idempotent-ish for the same event value, but
it's fragile — any recomposition (e.g. from a `LocalConfiguration` change, or any state read
inside this composable scope changing) re-fires the event. `docs/TODO.md` calls this out too:
"Remove the `DetailEvent` push from `MainScreen` before navigation" and "emit navigation effects
instead of navigating inline." The correct pattern is a `LaunchedEffect(detail)` keyed on the
route arguments, firing exactly once per distinct navigation.

**(c) ViewModels never scoped, never cleared.** `MainScreen.kt:91-97`:
```kotlin
val entryPoint: EntryPoint by rememberAppGraph()
val busViewModel: BusViewModel = entryPoint.busViewModel
val programViewModel: ProgramViewModel = entryPoint.programViewModel
val mapViewModel: MapViewModel = entryPoint.mapViewModel
val searchViewModel: SearchViewModel = entryPoint.searchViewModel
val detailViewModel: DetailViewModel = entryPoint.detailViewModel
val countdownViewModel: CountdownViewModel = entryPoint.countdownViewModel
```
All six come straight from the Metro `AppComponent` graph
(`app/src/main/kotlin/de/heilsen/ganzhornfest/di/AppComponent.kt`), a process-lifetime
`@DependencyGraph(AppScope::class)` singleton. `presenter-api/src/main/kotlin/de/heilsen/ganzhornfest/core/MoleculeViewModel.kt:14`
extends `androidx.lifecycle.ViewModel`, and its `models` `StateFlow` is built via
`scope.launchMolecule(...)` where `scope = CoroutineScope(viewModelScope.coroutineContext +
AndroidUiDispatcher.Main)` (`MoleculeViewModel.kt:15`). `ViewModel.onCleared()` is what normally
cancels `viewModelScope` — but nothing in this app ever constructs these six ViewModels through
`ViewModelProvider`/`viewModels()`/a `NavBackStackEntry` store, so `onCleared()` is never called
on any of them, for the entire app process lifetime. Concretely: **`BusPresenter`'s and
`ProgramPresenter`'s and `SearchPresenter`'s and `MapPresenter`'s `present()` composables keep
recomposing and their backing `collectAsState`-driven SQLDelight flows keep collecting, even
while the user is sitting on the Info tab with none of those screens visible.** This is also
the root cause behind why `SearchScreen` needs manual `SearchEvent.Clear` plumbing on
back/collapse (`SearchScreen.kt:82,97`) — the presenter's `remember { mutableStateOf(...) }`
state (`SearchPresenter.kt:22-23`) never resets naturally because the presenter itself is never
torn down between visits. `docs/TODO.md` names this directly too: "Scope screen state to the
`NavBackStackEntry` instead of using app-wide shared view models."

## Files owned

- `app/src/main/kotlin/de/heilsen/ganzhornfest/navigation/Destination.kt`
- `app/src/main/kotlin/de/heilsen/ganzhornfest/main/MainScreen.kt`
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/**` (`DetailEvent.kt`, `DetailModel.kt`,
  `DetailPresenter.kt`, `GetClubDetailUseCase.kt`, `GetOfferDetailUseCase.kt`)
- `data/src/main/kotlin/de/heilsen/ganzhornfest/club/data/ClubRepository.kt`,
  `data/src/main/kotlin/de/heilsen/ganzhornfest/poi/PoiRepository.kt`
- `database/src/main/sqldelight/de/heilsen/ganzhornfest/database/Poi.sq`,
  `ClubOffer.sq`, `Offer.sq` — adding ID-based query variants alongside (not replacing, until
  call sites migrate) the existing name-based ones
- `di-api/src/main/kotlin/de/heilsen/ganzhornfest/di/**` if the DI wiring for scoped ViewModels
  needs new pieces (Metro's story for nav-graph-scoped bindings needs research — see step 3)

This is the largest and most cross-cutting plan in the batch. Expect it to take meaningfully
longer than the others; consider whether it should itself be split once `feat/festival-data-2026`
lands and the real diff is visible.

## Steps

1. **Add stable IDs without breaking the seed data shape.** `Poi` and `Offer` already have
   `id INTEGER PRIMARY KEY` (`Poi.sq:1`, `Offer.sq` — confirm exact line). Change
   `Destination.Detail` to carry `val id: Long` (or a typed wrapper) instead of `val title:
   String`, keep `type: DetailType`. Add ID-based query variants
   (`selectClubById`/`selectOfferById`) alongside the existing `LIKE`-based name queries in
   `Poi.sq`/`Offer.sq` — do not delete the name-based ones yet, `search-impl`'s
   `ShowSearchResultsUseCaseImpl` still needs name search independent of this change, it just
   needs to additionally return the ID so its result-click handler can pass an ID forward
   instead of a title.
   *Verify:* `Destination.Detail(id = 42, type = DetailType.Club)` round-trips through
   `navController.navigate(...)` / `navBackStackEntry.toRoute<Destination.Detail>()`
   correctly (existing `kotlinx.serialization`-backed typed nav already handles non-String
   fields fine, e.g. check how other destinations serialize).

2. **Move the event dispatch into a `LaunchedEffect`.** Replace
   `detailViewModel.take(detailEvent)` at `MainScreen.kt:221` with:
   ```kotlin
   LaunchedEffect(detail) {
       detailViewModel.take(detailEvent)
   }
   ```
   keyed on the route arguments so it fires once per distinct navigation, not once per
   recomposition.
   *Verify:* add a `Log`/breakpoint (temporarily) confirming `take()` fires exactly once when
   navigating to a detail screen, not on every subsequent recomposition triggered by e.g.
   rotating the device.

3. **Scope ViewModels to the nav back stack entry.** This is the hard part. Research how Metro's
   DI integrates with `androidx.navigation`'s `NavBackStackEntry`-scoped `ViewModelStoreOwner` —
   Compose Navigation's `composable { }` builder gives each destination its own
   `ViewModelStoreOwner` via `LocalViewModelStoreOwner`, and the standard
   `viewModel()`/`hiltViewModel()`-style composable functions hook into that. This repo has no
   Hilt; Metro's own navigation-scoping story needs to be checked against its current version
   (`metro = "1.0.0-RC4"` per `libs.versions.toml`) — it may not have first-class nav-scope
   support yet, in which case the pragmatic fix is: keep ViewModels created via Metro (as now),
   but stop treating them as always-alive singletons by moving `detailViewModel` and (arguably)
   `searchViewModel` specifically off the `EntryPoint`-provided singleton pattern and into a
   factory the composable calls per-entry, backed by a `viewModel(viewModelStoreOwner =
   navBackStackEntry) { detailViewModelFactory.create() }`-style construction. Bus, Program, Map,
   Countdown ViewModels are reasonably long-lived (they're the four bottom-nav destinations,
   revisited constantly) — Detail is the one that's genuinely per-navigation and benefits most
   from real scoping. Consider scoping only `DetailViewModel` in this pass and leaving the
   bottom-nav four as-is with a comment explaining the tradeoff, rather than forcing all six into
   a scoping model that doesn't fit their actual usage pattern.
   *Verify:* navigate to a detail screen, verify (via a `Log` in `onCleared()` or a debugger)
   that `DetailViewModel.onCleared()` now actually fires on back navigation. This is the concrete
   proof the memory/flow-collection leak is fixed for at least the worst offender.

4. **Deep link support.** Once routes are ID-based, add `navDeepLink<Destination.Detail>(...)`
   entries so `Destination.Detail(id, type)` is reachable via an intent URI
   (`ganzhornfest://detail/{type}/{id}` or similar), per `docs/TODO.md`'s "Add deep-link and
   back stack restoration support once routes are ID-based." Add the corresponding
   `<intent-filter>` in `AndroidManifest.xml` if you want it externally invocable — check
   whether P6 (edge-to-edge) has already landed and touched the manifest, to avoid a stale-diff
   conflict; if it has, rebase onto it first.
   *Verify:* `adb shell am start -a android.intent.action.VIEW -d
   "ganzhornfest://detail/club/42"` opens the app directly to that club's detail screen.

## Tests

Add `MainScreenTest`-style navigation tests if `:app` has UI test infra suitable
(`androidx-ui-test-junit4` is present per `app/build.gradle.kts:156`, though check whether it's
actually used anywhere yet — `docs/TODO.md` separately lists "Add UI tests for navigation paths
such as Search -> Detail and Map -> Detail" as unstarted work, which this plan is a natural home
for). At minimum: a `DetailPresenterTest` update confirming ID-based lookup, and a manual
verification pass per step 3's `onCleared()` check above since ViewModel lifecycle isn't easily
unit-testable without real Compose Navigation infra.

## Done when

`./gradlew check` passes, `onCleared()` is confirmed firing for `DetailViewModel` on back
navigation, a deep link resolves correctly, then `/create-pr`.
