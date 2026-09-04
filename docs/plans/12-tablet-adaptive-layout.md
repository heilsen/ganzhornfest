# P12 · Tablet and landscape adaptive layout — Wave 3, blocked

## ⚠ Blocked

Wave 3. Two collisions:

- **P8 (`refactor/nav-ids-and-scoped-vms`) rewrites `app/.../main/MainScreen.kt`.** This plan
  replaces the `Scaffold` + `NavigationBar` in the same file. Start only after P8 has merged,
  then re-read `MainScreen.kt` fresh. The `file:line` citations below are against `main` at
  `e182fe6` and will drift.
- **P4 and P5 both own `bus-impl/**`.** Step 2 caps the content width of `BusScreen`. Land P4
  and P5 first.

Do not start this worktree until P8, P4 and P5 are on `main`.

## Goal

Programm, Info and Busfahrplan stop reading as a stretched phone on tablets and in landscape.
Navigation moves to a rail at medium width and up, and stays a bottom bar on compact.

## Why now

The tablet screenshots captured for the 2026 Play listing show the problem directly: full-bleed
single columns with a bottom `NavigationBar` pinned under 800 dp of white space on the Programm,
Info and Busfahrplan screens. Those are the `assets/playstore/2026/tablet-*.png` shots, which are
gitignored and local only. See `docs/playstore-capture.md`.

`theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/WindowLayout.kt:15-19` is the app's only
adaptive primitive. `isSidePanelLayout()` is consumed in exactly two places:
`app/.../main/MainScreen.kt:358` (Detail as a side pane) and `map/.../map/MapScreen.kt` (pin
editor gate). Nothing else in the app reacts to width.

`app/.../main/MainScreen.kt:144-240`: a `Scaffold` whose `bottomBar` is a four-item
`NavigationBar` (`Info`, `Karte`, `Programm`, `Busfahrplan`). It renders at the bottom at every
width. There is no `NavigationRail` or `NavigationSuiteScaffold` anywhere in the tree.

`grep -rn 'NavigationRail\|WindowSizeClass\|NavigationSuite' docs/plans/` returns nothing. No
existing plan covers this.

## Worktree

```bash
/start-implement feat tablet-adaptive-layout
```

## Files owned

- `app/src/main/kotlin/de/heilsen/ganzhornfest/main/MainScreen.kt` — swap the navigation
  container.
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/WindowLayout.kt` — a second helper for
  the navigation-type decision if `NavigationSuiteScaffold`'s default is not right.
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/` — a new `ConstrainedContent`
  composable for step 2.
- `program/src/main/kotlin/de/heilsen/ganzhornfest/program/ProgramScreen.kt`
- `bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusScreen.kt`
- `info-api` or wherever `InfoScreen` lives — the width cap only.
- `gradle/libs.versions.toml` — add `material3-adaptive-navigation-suite`.

**Do not touch:** `:map` internals (P9), `.sq` files, `Destination.kt` and navigation routing
(P8), presenter logic in any module.

## Steps

1. **Add the navigation-suite dependency.** `gradle/libs.versions.toml:60` already has
   `androidx.compose.material3.adaptive:adaptive:1.3.0`. Add
   `androidx.compose.material3:material3-adaptive-navigation-suite` next to it, same version
   catalog section. Wire it into `app/build.gradle.kts`.
   *Verify:* `./gradlew :app:dependencies` shows the module, project compiles.

2. **Cap and centre the single-column screens.** New `ConstrainedContent` composable in
   `:theme`: `Box(Modifier.fillMaxWidth())` with an inner
   `Modifier.widthIn(max = 600.dp).align(Alignment.TopCenter)`. Wrap the list content of
   `ProgramScreen`, `BusScreen` and `InfoScreen`. This is the cheapest change with the largest
   visual payoff. Do it first so it lands even if step 3 stalls.
   *Verify:* re-run the tablet capture from the Play-assets task
   (`adb shell wm size 1440x2560`, landscape). `03-programm.png` and `05-bus.png` show a
   centred column, not full-bleed.

3. **Replace `Scaffold` + `NavigationBar` with `NavigationSuiteScaffold`.** In `MainScreen.kt`,
   the four `NavigationBarItem` blocks become `NavigationSuiteScaffold`'s
   `navigationSuiteItems`. Its default `navigationSuiteType` from
   `currentWindowAdaptiveInfoV2()` gives a bottom bar in the Compact width class and a rail at
   Medium and up, which is exactly the desired behaviour. Keep the existing
   `NavigationBarItemDefaults.colors` styling via `NavigationSuiteItemColors`.
   *Verify:* on the phone emulator at default size the bottom bar is unchanged. At
   `wm size 1440x2560` landscape a left rail appears and the content fills the freed height.

4. **Optional: Programm as a multi-column grid at expanded width.** Only if steps 2 and 3 land
   cleanly. `LazyVerticalGrid(GridCells.Adaptive(320.dp))` for the ticket cards when
   `isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)`.
   *Verify:* two or more columns of ticket cards on the tablet, one column on the phone.

## Tests

Layout-only change, no presenter logic touched. `./gradlew check` (lint, ktlint, existing unit
tests) is the gate. Add a `ConstrainedContent` preview at two widths if `:theme` has a preview
pattern.

## Done when

`./gradlew check` passes, the tablet capture shows centred content and a rail, the phone is
visually unchanged, then `/create-pr`.
