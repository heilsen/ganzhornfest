# TODO

## High Priority

- [ ] Fix detail/navigation architecture
  - [x] Replace title-based detail routing with stable IDs
  - Scope screen state to the `NavBackStackEntry` instead of using app-wide shared view models
  - Remove the `DetailEvent` push from `MainScreen` before navigation
  - Add deep-link and back stack restoration support once routes are ID-based

- [ ] Fill in missing loading and error states
  - Show a proper loading state in `MapScreen`
  - Show a proper loading state in `DetailScreen`
  - Add a reusable error state for failed DB/data loads instead of blank screens

- [ ] Use real festival opening times in time-based features
  - Stop hardcoding the bus start time to `19:00`
  - Introduce opening hours per day and use them for bus/program filtering

## UX And Feature Work

- [ ] Improve detail and map flows
  - Finish detail-to-detail navigation from related items using IDs
  - [x] Replace the temporary placeholder in `DetailScreen` map interactions (detail is a sheet on the live map)

- [ ] Polish map presentation and performance
  - Revisit the default `HYBRID` map type for the main festival map
  - Cache/precompute `MarkerUi.icon` instead of creating a `BitmapDescriptor` on every access
  - Delete `MapModel.isFullscreen`
    - It defaults to true and is never set false. The camera padding branches that read it
      were collapsed into `SEARCH_BAR_CAMERA_INSET` plus the status bar inset, so the field
      is now unread.
  - [x] Keep the marker legend colors aligned with the actual marker colors (labels are
    still hardcoded German strings, not resources, tracked below)

- [ ] Finish Info and Program screen polish
  - Replace hardcoded counts/text in `InfoScreen` with DB-backed values and string resources
  - [x] Remove or fill the empty `Card` in `InfoScreen`
  - Rework the `ProgramScreen` into a timetable-style layout

- [ ] Consider small product additions
  - [x] Instagram deep link
  - In-app review prompt

## Architecture And Data

- [ ] Move navigation and state handling out of composables where it is currently side-effect driven
  - Remove the `MainScreen` navigation TODO by emitting navigation effects instead of navigating inline
  - Keep presenters/view models predictable and easier to test

- [ ] Move ViewModels off the app graph
  - Provide them through `viewModel()` with a Metro-backed factory instead of reading them from `EntryPoint` in composition
  - Unblocks scoping screen state to the `NavBackStackEntry`
  - `remember` at the read site in `MainScreen` is the interim workaround

- [ ] Reduce lifecycle-sensitive presenter state
  - Revisit presenter-local `mutableStateOf` usage in `BusPresenter`, `ProgramPresenter`, and `SearchPresenter`
  - Keep presenter state derivation consistent across features

- [ ] Tighten database schema integrity and query performance
  - Add explicit foreign keys where missing
  - Add indexes for frequent lookups used by search and other list/detail flows
  - [x] Add migration tests for SQLDelight (`MigrationPathTest`, one fixture per shipped release)

- [x] Improve info/statistics queries
  - [x] Add the missing count queries needed for `InfoScreen` instead of manually maintained numbers

## Quality, Tooling, And Release

- [ ] Expand automated tests
  - Add presenter/view model tests for bus, program, search, map, and detail flows
  - Add DB integration tests (migration path tests done, see `MigrationPathTest`)
  - Add UI tests for navigation paths such as Search -> Detail and Map -> Detail

- [ ] Add CI and static analysis
  - Add a CI pipeline for assemble, test, lint, and schema verification (done)
  - Add Ktlint (done, wired via the `ganzhornfest` convention plugin)
  - Enable Compose lint checks and address the findings (done, applied globally via the
    `ganzhornfest` convention plugin)
  - Add Detekt. Blocked: the last stable release (1.23.8) targets AGP 8.8.1 / Gradle
    8.12.1 / Kotlin 2.0.21, well behind this repo's AGP 9.2.0 / Gradle 9.4.1 / Kotlin
    2.2.20. The maintained line moved to plugin id `dev.detekt` and only exists as
    `2.0.0-alpha.x`, which has had Gradle configuration-cache serialization failures.
    Revisit once a stable `dev.detekt` release lands.

- [ ] Improve build and repo hygiene
  - Stop reading `local.properties` during Gradle configuration

- [x] Modernize Android setup
  - [x] Add edge-to-edge/insets handling
    - The map draws behind the status bar under a theme-adaptive scrim, and the chrome
      over it re-applies the top inset for itself.
    - The expanded search bar clears the keyboard. `adjustResize` is set for API 24 to 29.
  - [x] Replace the AppCompat theme parent and move the splash to core-splashscreen, so
    API 24 to 30 gets a themed splash too
  - [x] Enable release resource shrinking
  - [x] Declare `android:roundIcon`

- [x] Update project documentation
  - [x] Rewrite the README to describe the current Kotlin/Compose/SQLDelight app instead of the old Ionic/Cordova stack
    - Also fixed machine-local absolute links, a module that does not exist, the bottom
      nav order, and the missing `LICENSE` file the README claimed.

- [ ] Improve release readiness and privacy
  - [x] Standardize logging and avoid sensitive logs in release builds
    - Timber was already the single facade. The leak was `ShowSearchResultsUseCaseImpl`
      logging the raw search term at INFO on every emission, now removed. The
      `CrashlyticsTree` priority floor moved from the `log()` body into `isLoggable()`,
      so nothing below WARN is even formatted, and `DebugTree` is debug-only, so in
      release VERBOSE, DEBUG and INFO have no sink at all.
    - `bus_destination` stays. It is one of four fixed village names, never free text.
  - [ ] Surface third-party licences through cashapp/licensee (`app.cash.licensee`)
    - Check AGP 9.x / Gradle 9.5 compatibility before picking a version. Do not pin one
      until that is confirmed.
    - Licensee reports the POM-declared licences of Gradle dependencies, so it covers the
      library set but not bundled assets. The two OFL font texts
      (`theme/src/main/res/raw/ofl_fraunces.txt`, `ofl_source_sans_3.txt`) are not in its
      output and need a hand-written entry shown alongside the generated list.
    - Those two files are referenced by nothing and are only kept in the APK by
      `theme/src/main/res/raw/keep.xml`. A screen that reads them removes that need.

- [ ] Finish resource, accessibility, and preview coverage
  - Move remaining hardcoded UI text into string resources
  - Review content descriptions, touch targets, focus order, and color contrast
  - Add previews for the major screens and important component states
