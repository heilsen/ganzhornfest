# P12 · Tablet and landscape adaptive layout — Wave 3

## Goal

Programm, Info and Busfahrplan stop reading as a stretched phone on tablets and in landscape.
Navigation moves to whichever component Material recommends for the window. Content below the
top app bars is capped to a centred 600 dp column.

## Why now

The tablet screenshots captured for the 2026 Play listing show the problem directly: full-bleed
single columns with a bottom `NavigationBar` pinned under 800 dp of white space on the Programm,
Info and Busfahrplan screens. Those are the `assets/playstore/2026/tablet-*.png` shots, which are
gitignored and local only. See `docs/playstore-capture.md`.

`theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/WindowLayout.kt` is the app's only other
adaptive primitive. `isSidePanelLayout()` is consumed in exactly two places: `MainScreen.kt`
(Detail as a side pane) and `map/.../map/MapScreen.kt` (pin editor gate). This plan leaves that
file alone.

## Files owned

- `app/src/main/kotlin/de/heilsen/ganzhornfest/main/MainScreen.kt` — swap the navigation
  container.
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/ConstrainedContent.kt` — new,
  the width cap.
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/GanzhornfestScaffold.kt` — wrap
  its content slot in the cap, so Programm and Bus need no edit.
- `info-api/src/main/kotlin/de/heilsen/ganzhornfest/info/InfoScreen.kt` — cap the card column,
  keep the scroll full width.
- `gradle/libs.versions.toml`, `app/build.gradle.kts` — the navigation-suite dependency, which
  bumps material3 to 1.4.0.

**Do not touch:** `ProgramScreen.kt` and `BusScreen.kt` (the cap reaches them through
`GanzhornfestScaffold`), `WindowLayout.kt`, `:map` internals, `.sq` files, `Destination.kt` and
navigation routing, presenter logic in any module.

## Steps

1. **Add the navigation-suite dependency.** `androidx.compose.material3:material3-adaptive-navigation-suite`
   on the same version ref as material3. Its POM pins material3 to `[1.4.0]` strictly, so the
   `androidx-compose-material` alias goes 1.3.2 to 1.4.0. That bump drops the transitive
   `material-icons-core`, so `theme`, `info-api` and `map` now declare it.
   *Verify:* `./gradlew check` before any code change.

2. **Cap and centre the single-column screens.** New `ConstrainedContent` composable in
   `:theme`: a `Box` filling the width with an inner `Modifier.widthIn(max = 600.dp)` centred.
   Wrap the content slot of `GanzhornfestScaffold`, which covers Programm and Bus with no edit
   to either file. Wrap the card column in `InfoScreen` by hand, since it owns its own
   `Scaffold`. The scroll stays full width so a fling anywhere works.
   *Verify:* `./gradlew check`. Render the `ConstrainedContent` preview.

3. **Replace `Scaffold` + `NavigationBar` with `NavigationSuiteScaffold`.** In `MainScreen.kt`
   the four `NavigationBarItem` blocks become `NavigationSuiteItem` calls inside
   `NavigationSuiteScaffold` from `material3-adaptive-navigation-suite`. One
   `NavigationSuiteType` value drives them, read straight from
   `NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfoV2())` with no
   app-side override. It returns the expressive types: compact width gets
   `ShortNavigationBarCompact`, a wide but short window gets `ShortNavigationBarMedium` with
   icons beside labels, everything wider and taller gets `WideNavigationRailCollapsed`. The
   components are the expressive `ShortNavigationBar` and `WideNavigationRail`, both stable in
   1.4.0. A bar-less `Scaffold` stays nested inside purely to keep producing the `innerPadding`
   every route already consumes.
   *Verify:* `./gradlew :app:assembleDebug`, then the manual pass on phone portrait, phone
   landscape and a tablet.

## Tests

Layout-only change, no presenter logic touched. `./gradlew check` is the gate.

## Out of scope

- Any app-side override of the Material navigation type, including forcing a rail on a landscape
  phone.
- `LazyVerticalGrid` for Programm at expanded width. `docs/plans/03-program-timetable.md`
  rewrites that file into a timetable anyway.
- Info's two-column layout and its 220 dp `LargeTopAppBar` hero.
- Overriding `WideNavigationRail`'s window insets for a display cutout.

## Done when

`./gradlew check` passes, the manual pass shows capped content and the recommended navigation
component at each size, then `/create-pr`.
