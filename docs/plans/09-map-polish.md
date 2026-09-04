# P9 · Map polish: marker performance and crash-proofing — Wave 2, blocked

## ⚠ Blocked

`feat/festival-data-2026` currently has uncommitted changes across essentially all of `:map`:
`MapScreen.kt`, `MapPresenter.kt`, `MapModel.kt`, `MarkerUi.kt`, `MarkerUiType.kt`,
`GetMarkersUseCase.kt`, `ClubCoordinatesRepository.kt`, `MapEvent.kt`, plus new files
`ClubPin.kt`, `GetClubPinsUseCase.kt`, `PinEditorPanel.kt`. This plan touches the same files.
**Do not start this worktree until `feat/festival-data-2026` has merged to `main`.** The branch
already fixes two things this plan would otherwise flag: it adds a `MarkerUiType.ATTRACTION`
case and caches marker icon lookups differently — re-read the post-merge files before starting,
since some of what's described below may already be resolved.

## Goal

Stop allocating a new `BitmapDescriptor` per marker per recomposition, stop hard-crashing the
whole map on one bad database row, keep the on-screen legend colors in sync with the actual
marker colors, and let the map center on a specific club when arriving from detail or search.

## Why now

**Allocation on every read.** `map/src/main/kotlin/de/heilsen/ganzhornfest/map/MarkerUi.kt:13-26`:
```kotlin
val MarkerUi.icon: BitmapDescriptor
    get() {
        fun convertMarkerUiTypeToMarkerHue(markerUiType: MarkerUiType) = when (markerUiType) { ... }
        return BitmapDescriptorFactory.defaultMarker(convertMarkerUiTypeToMarkerHue(markerUiType))
    }
```
This is declared as an extension **property**, but its body is a `get()` block that calls
`BitmapDescriptorFactory.defaultMarker(...)` fresh every single time `.icon` is read. It reads
like a cached value (the `val` keyword) but behaves like a function call. `MapScreen.kt:73-76`
reads `marker.icon` inside a `for (marker in mapModel.markers)` loop on every recomposition of
`MapScreen` — every marker's `BitmapDescriptor` (a real native Bitmap-backed Maps SDK resource)
gets reallocated on every recomposition, not just when markers change. `docs/TODO.md` names this
exactly: "Cache/precompute `MarkerUi.icon` instead of creating a `BitmapDescriptor` on every
access."

**Hard crash on bad data.** `map/src/main/kotlin/de/heilsen/ganzhornfest/map/GetMarkersUseCase.kt:24-30`:
```kotlin
markerUiType = when (type) {
    "club" -> MarkerUiType.CLUB
    ...
    else -> error("markerUiType='$type' is not a known marker type")
}
```
`error(...)` throws `IllegalStateException`. This runs inside a `.map { }` transform on a `Flow`
collected by `MapPresenter.present()` via `collectAsState` (`MapPresenter.kt:17`) — an uncaught
exception here propagates up through Compose's recomposition and crashes the whole app, not just
the map screen, for every single user, the moment any `poiType` row in the DB has a value this
`when` doesn't recognize. Given this repo's own yearly data-update workflow
(`docs/festival-update-workflow.md`) involves hand-editing SQL migrations, a typo'd type string
shipped in a migration is a completely realistic way to crash the app for the entire festival
weekend with no client-side recovery. This should record a non-fatal (via P1's `CrashReporter`,
once that lands — if P1 hasn't merged yet, use `Timber.e` as an interim and file a follow-up) and
skip the offending marker rather than propagating.

**Legend/marker color drift.** `MapScreen.kt:106-169`'s `Legend` composable hardcodes six
`Color(0xFF......)` literals in a fixed `Row`/`Box` layout, entirely independent of the hues
`MarkerUi.icon` actually uses (`BitmapDescriptorFactory.HUE_VIOLET`, `HUE_MAGENTA`, etc. at
`MarkerUi.kt:17-22`). `BitmapDescriptorFactory.HUE_*` constants are hue values (0-360) for HSV
markers, not sRGB — there's no shared source of truth converting one to the other, so any change
to one side won't be reflected in the other without a human noticing and manually updating both
places. `docs/TODO.md`: "Keep the marker legend aligned with actual marker colors and resource
strings."

**Can't center on a club.** `MapScreen.kt:40-41`:
```kotlin
// TODO: center around the club in the details screen
val center = LatLng(49.191669847836216, 9.222756134219502)
```
Hardcoded town-center coordinate regardless of navigation origin. `docs/TODO.md`: "Allow the map
to center on the selected club when entered from detail/search."

**`MapType.HYBRID`.** `MapScreen.kt:66` sets `mapType = MapType.HYBRID` (satellite + labels) as
the default for the whole festival map at `minZoomPreference = 16f`
(`MapScreen.kt:67`). Satellite imagery at that zoom over a dense town square is visually noisy
against colored pin markers. `docs/TODO.md`: "Revisit the default `HYBRID` map type."

## Files owned

- `map/src/main/kotlin/de/heilsen/ganzhornfest/map/**` (whatever shape it's in post-merge)
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/GetClubDetailUseCase.kt` (only the
  `MapModel` construction at the end, for the centering fix — check whether P4 or P8 have
  already touched this file and rebase accordingly)

Re-derive this list against `main` after `feat/festival-data-2026` merges — the file set and
even the marker type enum will have changed.

## Steps

1. **Cache marker icons.** Replace the property-with-getter in `MarkerUi.kt` with a
   precomputed `Map<MarkerUiType, BitmapDescriptor>` built once (e.g. a top-level `object` or a
   value injected once per `MapScreen` composition via `remember`), keyed by
   `MarkerUiType`. `BitmapDescriptor` instances from `BitmapDescriptorFactory.defaultMarker(hue)`
   are safe to share across multiple `Marker` composables simultaneously — there's no per-marker
   mutable state in a hue-based descriptor.
   *Verify:* confirm via a quick instrumented check or just code review that
   `BitmapDescriptorFactory.defaultMarker` is called exactly once per `MarkerUiType` value for
   the lifetime of the map screen, not once per marker per recomposition. If feasible, a simple
   counter in a debug build confirms this empirically.

2. **Stop crashing on unknown POI types.** Change `GetMarkersUseCase`'s `when (type) { ... else
   -> error(...) }` to `else -> { crashReporter.recordNonFatal(...); null }` inside the `.map {
   }` transform, then `.filterNotNull()` (or `mapNotNull`) the resulting list so an unrecognized
   marker is silently dropped from the map instead of crashing it. If P1 hasn't merged yet,
   substitute `Timber.e("Unknown markerUiType='$type'")` and leave a `// TODO: report via
   CrashReporter once P1 lands` comment.
   *Verify:* a unit test feeding `GetMarkersUseCase` a POI coordinate row with an unrecognized
   `type` string, asserting the resulting marker set excludes it rather than throwing.

3. **Derive the legend from the same source as the markers.** Replace `Legend`'s hardcoded
   `Color(0xFF......)` literals with a lookup that converts each `MarkerUiType`'s
   `BitmapDescriptorFactory.HUE_*` value to an approximate `androidx.compose.ui.graphics.Color`
   via `android.graphics.Color.HSVToColor(floatArrayOf(hue, 1f, 1f))`, so both the legend and the
   actual pins derive from one `MarkerUiType -> hue` mapping (the same one from step 1's cache).
   Also replace the legend's hardcoded label strings ("Veranstaltungsort", "Stand", etc.) with
   string resources, following the existing pattern of `bus_screen_title` living in
   `bus-api/src/main/res/values/strings.xml` — check whether `:map` has a `strings.xml` yet, add
   one if not.
   *Verify:* preview screenshot comparison confirms legend swatches visually match marker pin
   colors on the actual `GoogleMap`.

4. **Center on the selected club.** `GetClubDetailUseCase.kt`'s `MapModel.Data` construction
   already has access to the club's `coordinates` flow (`GetClubDetailUseCase.kt:22-28`). Thread
   a "focus point" through `MapModel` (e.g. `MapModel.Data.focusOn: LatLng?`) and have
   `MapScreen`'s `cameraPositionState` initialize from it when non-null, falling back to the
   hardcoded town-center default only for the general festival map view (`MapPresenter`'s
   `MapModel.Data` construction, not `GetClubDetailUseCase`'s).
   *Verify:* navigate Search → a club's detail screen, confirm the embedded map is centered on
   that club's marker, not the town center.

5. **Reconsider `MapType.HYBRID`.** Try `MapType.NORMAL` (vector map, high contrast against
   colored pins) as the new default; keep `HYBRID` as a user-toggleable option if there's an
   obvious place to put a toggle (e.g. a small map-type icon button), but don't invent new UI
   chrome just for this if it doesn't fit — a straight default change with a one-line rationale
   in the PR description is a legitimate, smaller resolution too.
   *Verify:* visual comparison at `minZoomPreference = 16f` (`MapScreen.kt:67`) over the actual
   festival area bounds (`MapScreen.kt:48-52`).

## Tests

Check `map/build.gradle.kts` for existing test deps (likely absent, add per the
`feature/search-impl` pattern if so). Add:
- `GetMarkersUseCaseTest` covering the unknown-type-is-dropped-not-thrown behavior (step 2) —
  this is the most important test in this plan, since it's directly preventing a
  whole-app-crash regression.
- A small unit test or manual verification for the hue→Color conversion in step 3, since a
  wrong conversion would silently desync the legend again in a different way.

## Done when

`./gradlew check` passes, the crash-on-unknown-type is confirmed fixed with a test, then
`/create-pr`.
